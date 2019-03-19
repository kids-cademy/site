package com.kidscademy.impl;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kidscademy.AtlasService;
import com.kidscademy.BusinessRules;
import com.kidscademy.atlas.Instrument;
import com.kidscademy.atlas.Link;
import com.kidscademy.atlas.MediaSRC;
import com.kidscademy.atlas.MediaWrapper;
import com.kidscademy.atlas.Picture;
import com.kidscademy.atlas.UIObject;
import com.kidscademy.atlas.User;
import com.kidscademy.dao.AtlasDao;
import com.kidscademy.tool.AudioProcessor;
import com.kidscademy.tool.AudioSampleInfo;
import com.kidscademy.tool.ImageInfo;
import com.kidscademy.tool.ImageProcessor;
import com.kidscademy.util.Files;
import com.kidscademy.util.Strings;
import com.kidscademy.www.SoftSchools;
import com.kidscademy.www.Wikipedia;
import com.kidscademy.www.WikipediaPageSummary;

import js.core.AppContext;
import js.http.form.Form;
import js.http.form.UploadedFile;
import js.log.Log;
import js.log.LogFactory;
import js.rmi.BusinessException;
import js.util.Params;

public class AtlasServiceImpl implements AtlasService {
    private static final Log log = LogFactory.getLog(AtlasServiceImpl.class);

    private final AppContext context;
    private final AtlasDao dao;
    private final AudioProcessor audio;
    private final ImageProcessor image;
    private final Wikipedia wikipedia;
    private final SoftSchools softSchools;

    public AtlasServiceImpl(AppContext context, AtlasDao dao, AudioProcessor audio, ImageProcessor image,
	    Wikipedia wikipedia, SoftSchools softSchools) {
	log.trace("AtlasServiceImpl(AppContext, AtlasDao, AudioProcessor, ImageProcessor, Wikipedia, SoftSchools)");
	this.context = context;
	this.dao = dao;
	this.audio = audio;
	this.image = image;
	this.wikipedia = wikipedia;
	this.softSchools = softSchools;
    }

    @Override
    public List<UIObject> getInstruments() {
	return dao.getInstruments();
    }

    @Override
    public Instrument getInstrument(int instrumentId) throws IOException {
	if (instrumentId == 0) {
	    User user = context.getUserPrincipal();
	    return Instrument.create(user);
	}
	Instrument instrument = dao.getInstrument(instrumentId);

	if (instrument.getSampleSrc() != null) {
	    File sampleFile = Files.mediaFile(instrument.getSampleSrc());
	    if (sampleFile.exists()) {
		AudioSampleInfo sampleInfo = audio.getAudioFileInfo(sampleFile);
		instrument.setSampleInfo(sampleInfo);
	    }
	}

	return instrument;
    }

    @Override
    public Instrument getInstrumentByName(String name) {
	return dao.getInstrumentByName(name);
    }

    @Override
    public Instrument saveInstrument(Instrument instrument) throws IOException {
	if (instrument.getSampleSrc() != null) {
	    MediaFileHandler handler = new MediaFileHandler(instrument, "sample.mp3");
	    handler.commit();
	    instrument.setSampleSrc(handler.sourceSrc());
	    instrument.setWaveformSrc(generateWaveform(instrument, handler.source()));
	}

	if (instrument.getPictures() != null) {
	    for (Picture picture : instrument.getPictures()) {
		MediaFileHandler handler = new MediaFileHandler(instrument, picture.getFileName());
		handler.commit();
	    }
	}

	dao.saveInstrument(instrument);
	return instrument;
    }

    @Override
    public List<UIObject> getRelatedInstruments(List<String> names) {
	return dao.findObjectsByName(Instrument.class, names);
    }

    @Override
    public List<UIObject> getAvailableInstruments(Instrument.Category category, List<UIObject> related) {
	List<UIObject> instruments = dao.getInstrumentsByCategory(category);
	instruments.removeAll(related);
	return instruments;
    }

    @Override
    public Link createLink(URL url) {
	return Link.create(url);
    }

    @Override
    public String importObjectDescription(Link link) {
	switch (link.getDomain()) {
	case "softschools.com":
	    return Strings.html(softSchools.getFacts(link.getPath()).getDescription());

	case "wikipedia.org":
	    WikipediaPageSummary summary = wikipedia.getPageSummary(link.getFileName());
	    return summary != null ? Strings.html(summary.getExtract()) : null;

	default:
	    return null;
	}
    }

    @Override
    public Map<String, String> importObjectsFacts(Link link) {
	switch (link.getDomain()) {
	case "softschools.com":
	    Map<String, String> facts = new HashMap<>();
	    for (String fact : softSchools.getFacts(link.getPath()).getFacts()) {
		facts.put(Strings.excerpt(fact), fact);
	    }
	    return facts;

	default:
	    return null;
	}
    }

    // ----------------------------------------------------------------------------------------------
    // OBJECT IMAGE SERVICES

    @Override
    public Picture uploadPicture(Form form) throws IOException, BusinessException {
	UploadedFile uploadedFile = form.getUploadedFile("media-file");
	return upload(form, uploadedFile.getFile());
    }

    @Override
    public Picture uploadPictureBySource(Form form) throws IOException, BusinessException {
	Params.notNull(form.getValue("source"), "Picture source");
	URL url = new URL(form.getValue("source"));
	return upload(form, Files.copy(url));
    }

    private Picture upload(Form form, File file) throws IOException, BusinessException {
	int objectId = Integer.parseInt(form.getValue("object-id"));
	String pictureName = form.getValue("name");
	String pictureKind = form.getValue("kind");

	Params.notZero(objectId, "Object ID");
	Params.notNullOrEmpty(pictureName, "Picture name");
	Params.notNullOrEmpty(pictureKind, "Picture kind");

	BusinessRules.uniquePictureName(objectId, pictureName);
	BusinessRules.transparentFeaturedPicture(pictureKind, file);

	ImageInfo imageInfo = image.getImageInfo(file);

	UIObject object = new UIObject(form.getValue("object-dtype"), form.getValue("object-name"));
	File targetFile = Files.mediaFile(object, pictureKind, imageInfo.getType().extension());
	targetFile.getParentFile().mkdirs();
	targetFile.delete();

	if (!file.renameTo(targetFile)) {
	    throw new IOException("Unable to upload " + targetFile);
	}

	Picture picture = new Picture();
	picture.setName(pictureName);
	picture.setKind(pictureKind);
	picture.setUploadDate(new Date());
	picture.setSource(form.getValue("source"));
	picture.setFileName(targetFile.getName());

	picture.setFileSize(imageInfo.getFileSize());
	picture.setWidth(imageInfo.getWidth());
	picture.setHeight(imageInfo.getHeight());

	dao.addObjectPicture(objectId, picture);

	picture.setSrc(Files.mediaSrc(object, targetFile.getName()));
	return picture;
    }

    @Override
    public Picture trimPicture(UIObject object, Picture picture) throws IOException {
	MediaFileHandler handler = new MediaFileHandler(object, picture.getFileName());
	image.trim(handler.source(), handler.target());
	updatePicture(picture, handler.target(), handler.targetSrc());
	return picture;
    }

    @Override
    public Picture flopPicture(UIObject object, Picture picture) throws IOException {
	MediaFileHandler handler = new MediaFileHandler(object, picture.getFileName());
	image.flop(handler.source(), handler.target());
	updatePicture(picture, handler.target(), handler.targetSrc());
	return picture;
    }

    @Override
    public Picture flipPicture(UIObject object, Picture picture) throws IOException {
	MediaFileHandler handler = new MediaFileHandler(object, picture.getFileName());
	image.flip(handler.source(), handler.target());
	updatePicture(picture, handler.target(), handler.targetSrc());
	return picture;
    }

    @Override
    public Picture cropPicture(UIObject object, Picture picture, int width, int height, int xoffset, int yoffset)
	    throws IOException {
	MediaFileHandler handler = new MediaFileHandler(object, picture.getFileName());
	image.crop(handler.source(), handler.target(), width, height, xoffset, yoffset);
	updatePicture(picture, handler.target(), handler.targetSrc());
	return picture;
    }

    @Override
    public void removePicture(UIObject object, Picture picture) throws IOException {
	MediaFileHandler handler = new MediaFileHandler(object, picture.getFileName());
	handler.delete();
	dao.removeObjectPicture(object.getId(), picture);
    }

    @Override
    public Picture commitPicture(UIObject object, Picture picture) throws IOException {
	MediaFileHandler handler = new MediaFileHandler(object, picture.getFileName());
	handler.commit();
	updatePicture(picture, handler.source(), handler.sourceSrc());
	return picture;
    }

    @Override
    public void rollbackPicture(UIObject object, Picture picture) throws IOException {
	MediaFileHandler handler = new MediaFileHandler(object, picture.getFileName());
	handler.rollback();
    }

    @Override
    public Picture undoPicture(UIObject object, Picture picture) throws IOException {
	MediaFileHandler handler = new MediaFileHandler(object, picture.getFileName());
	handler.undo();
	updatePicture(picture, handler.source(), handler.sourceSrc());
	return picture;
    }

    private void updatePicture(Picture picture, File file, MediaSRC src) throws IOException {
	ImageInfo info = image.getImageInfo(file);
	picture.setFileSize(info.getFileSize());
	picture.setWidth(info.getWidth());
	picture.setHeight(info.getHeight());
	picture.setSrc(src);
    }

    // ----------------------------------------------------------------------------------------------
    // OBJECT AUDIO SAMPLE SERVICES

    @Override
    public AudioSampleInfo uploadAudioSample(Form form) throws IOException {
	UIObject object = new UIObject(form.getValue("dtype"), form.getValue("name"));
	MediaFileHandler handler = new MediaFileHandler(object, "sample.mp3");
	handler.upload(form.getFile("file"));
	return getAudioSampleInfo(object, handler.source(), handler.sourceSrc());
    }

    @Override
    public AudioSampleInfo normalizeAudioSample(UIObject object) throws IOException {
	MediaFileHandler handler = new MediaFileHandler(object, "sample.mp3");
	audio.normalizeLevel(handler.source(), handler.target());
	if (handler.target().exists()) {
	    return getAudioSampleInfo(object, handler.target(), handler.targetSrc());
	}
	return getAudioSampleInfo(object, handler.source(), handler.sourceSrc());
    }

    @Override
    public AudioSampleInfo convertAudioSampleToMono(UIObject object) throws IOException {
	MediaFileHandler handler = new MediaFileHandler(object, "sample.mp3");
	audio.convertToMono(handler.source(), handler.target());
	return getAudioSampleInfo(object, handler.target(), handler.targetSrc());
    }

    @Override
    public AudioSampleInfo trimAudioSampleSilence(UIObject object) throws IOException {
	MediaFileHandler handler = new MediaFileHandler(object, "sample.mp3");
	audio.trimSilence(handler.source(), handler.target());
	return getAudioSampleInfo(object, handler.target(), handler.targetSrc());
    }

    @Override
    public AudioSampleInfo cutAudioSample(UIObject object, float start) throws IOException {
	MediaFileHandler handler = new MediaFileHandler(object, "sample.mp3");
	audio.cutSegment(handler.source(), handler.target(), start, start + 30);
	return getAudioSampleInfo(object, handler.target(), handler.targetSrc());
    }

    @Override
    public AudioSampleInfo fadeInAudioSample(UIObject object) throws IOException {
	MediaFileHandler handler = new MediaFileHandler(object, "sample.mp3");
	audio.fadeIn(handler.source(), handler.target(), 1.5F);
	return getAudioSampleInfo(object, handler.target(), handler.targetSrc());
    }

    @Override
    public AudioSampleInfo fadeOutAudioSample(UIObject object) throws IOException {
	MediaFileHandler handler = new MediaFileHandler(object, "sample.mp3");
	audio.fadeOut(handler.source(), handler.target(), 1.5F);
	return getAudioSampleInfo(object, handler.target(), handler.targetSrc());
    }

    @Override
    public MediaSRC generateWaveform(UIObject object) throws IOException {
	Params.notZero(object.getId(), "Object ID");

	File sampleFile = Files.mediaFile(object, "sample.mp3");
	if (!sampleFile.exists()) {
	    log.error("Database not consistent. Missing sample file |%s|. Reset sample and waveform for object |%s|.",
		    sampleFile, object.getName());
	    dao.resetObjectSample(object.getDtype(), object.getId());
	    return null;
	}
	return generateWaveform(object, sampleFile);
    }

    @Override
    public AudioSampleInfo undoAudioSampleProcessing(UIObject object) throws IOException {
	MediaFileHandler handler = new MediaFileHandler(object, "sample.mp3");
	handler.undo();
	return getAudioSampleInfo(object, handler.source(), handler.sourceSrc());
    }

    @Override
    public AudioSampleInfo rollbackAudioSampleProcessing(UIObject object) throws IOException {
	MediaFileHandler handler = new MediaFileHandler(object, "sample.mp3");
	handler.rollback();
	return getAudioSampleInfo(object, handler.source(), handler.sourceSrc());
    }

    @Override
    public void removeAudioSample(UIObject object) throws IOException {
	Params.notZero(object.getId(), "Object ID");

	MediaFileHandler handler = new MediaFileHandler(object, "sample.mp3");
	handler.delete();
	dao.resetObjectSample(object.getDtype(), object.getId());
	Files.mediaFile(object, "sample.mp3").delete();
	Files.mediaFile(object, "waveform.png").delete();
    }

    // ----------------------------------------------------------------------------------------------

    private AudioSampleInfo getAudioSampleInfo(MediaWrapper object, File file, MediaSRC mediaSrc) throws IOException {
	AudioSampleInfo info = audio.getAudioFileInfo(file);
	info.setSampleSrc(mediaSrc);
	info.setWaveformSrc(generateWaveform(object, file));
	return info;
    }

    private MediaSRC generateWaveform(MediaWrapper object, File audioFile) throws IOException {
	MediaSRC waveformSrc = Files.mediaSrc(object, "waveform.png");
	File waveformFile = Files.mediaFile(waveformSrc);
	audio.generateWaveform(audioFile, waveformFile);
	return waveformSrc;
    }
}
