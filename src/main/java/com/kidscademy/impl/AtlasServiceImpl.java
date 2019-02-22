package com.kidscademy.impl;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kidscademy.AtlasService;
import com.kidscademy.atlas.Instrument;
import com.kidscademy.atlas.Link;
import com.kidscademy.atlas.MediaSRC;
import com.kidscademy.atlas.MediaWrapper;
import com.kidscademy.atlas.UIObject;
import com.kidscademy.atlas.User;
import com.kidscademy.dao.AtlasDao;
import com.kidscademy.tool.AudioProcessor;
import com.kidscademy.tool.AudioSampleInfo;
import com.kidscademy.tool.ImageInfo;
import com.kidscademy.tool.ImageProcessor;
import com.kidscademy.util.Files;
import com.kidscademy.www.SoftSchools;
import com.kidscademy.www.Wikipedia;
import com.kidscademy.www.WikipediaPageSummary;

import js.core.AppContext;
import js.http.form.Form;
import js.log.Log;
import js.log.LogFactory;
import js.util.Params;
import js.util.Strings;

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
    public String importObjectDescription(UIObject object) {
	List<Link> links = dao.getObjectLinks(object);
	if (links.isEmpty()) {
	    return null;
	}

	for (Link link : links) {
	    if ("Soft Schools".equals(link.getName())) {
		// http://www.softschools.com/facts/music_instruments/accordion_facts/3037/
		// TODO magic number
		String path = link.getUrl().getPath().substring(6);
		return "<p>" + softSchools.getFacts(path).getDescription().replaceAll("\\. ", ".</p><p>") + "</p>";
	    }
//	    if ("Wikipedia".equals(link.getName())) {
//		WikipediaPageSummary summary = wikipedia.getPageSummary(link.getFileName());
//		return summary != null ? summary.getExtract() : null;
//	    }
	}

	return null;
    }

    @Override
    public Map<String, String> importObjectsFacts(UIObject object) {
	List<Link> links = dao.getObjectLinks(object);
	if (links.isEmpty()) {
	    return null;
	}

	for (Link link : links) {
	    if ("Soft Schools".equals(link.getName())) {
		// http://www.softschools.com/facts/music_instruments/accordion_facts/3037/
		// TODO magic number
		String path = link.getUrl().getPath().substring(6);

		Map<String, String> facts = new HashMap<>();
		for (String fact : softSchools.getFacts(path).getFacts()) {
		    int firstPuctuationIndex = Strings.indexOneOf(fact, ',', ';', '.');
		    if (firstPuctuationIndex == -1) {
			firstPuctuationIndex = fact.length();
		    }
		    facts.put(fact.substring(0, Math.min(firstPuctuationIndex, 64)), fact);
		}
		return facts;
	    }
	}

	return null;
    }

    // ----------------------------------------------------------------------------------------------
    // OBJECT IMAGE SERVICES

    @Override
    public ImageInfo getImageInfo(UIObject object, String imageName) throws IOException {
	MediaFileHandler handler = new MediaFileHandler(object, imageName);
	return image.getImageInfo(handler.source());
    }

    @Override
    public MediaSRC uploadPictureFile(Form form) throws IOException {
	String collectionName = form.getValue("dtype");
	String objectName = form.getValue("name");

	MediaSRC pictureSrc = Files.mediaSrc(collectionName, objectName, "picture.jpg");
	File pictureFile = Files.mediaFile(pictureSrc);
	pictureFile.getParentFile().mkdirs();

	image.saveObjectPicture(form.getUploadedFile("file").getFile(), pictureFile);
	return pictureSrc;
    }

    @Override
    public MediaSRC uploadThumbnailFile(Form form) throws IOException {
	String collectionName = form.getValue("dtype");
	String objectName = form.getValue("name");

	MediaSRC thumbnailSrc = Files.mediaSrc(collectionName, objectName, "thumbnail.png");
	File thumbnailFile = Files.mediaFile(thumbnailSrc);
	thumbnailFile.getParentFile().mkdirs();

	image.saveObjectThumbnail(form.getUploadedFile("file").getFile(), thumbnailFile);
	return thumbnailSrc;
    }

    @Override
    public MediaSRC uploadIconFile(Form form) throws IOException {
	String collectionName = form.getValue("dtype");
	String objectName = form.getValue("name");

	MediaSRC iconSrc = Files.mediaSrc(collectionName, objectName, "icon.jpg");
	File iconFile = Files.mediaFile(iconSrc);
	iconFile.getParentFile().mkdirs();

	image.saveObjectIcon(form.getUploadedFile("file").getFile(), iconFile);
	return iconSrc;
    }

    @Override
    public MediaSRC createObjectIcon(String collectionName, String objectName) throws IOException {
	UIObject object = new UIObject(collectionName, objectName);
	File pictureFile = Files.mediaFile(object, "picture.jpg");

	MediaSRC iconSrc = Files.mediaSrc(collectionName, objectName, "icon.jpg");
	File iconFile = Files.mediaFile(iconSrc);

	image.createObjectIcon(pictureFile, iconFile);
	return iconSrc;
    }

    @Override
    public MediaSRC cropObjectImage(UIObject object, String imageName, int width, int height, int xoffset, int yoffset)
	    throws IOException {
	MediaFileHandler handler = new MediaFileHandler(object, imageName);
	image.crop(handler.source(), handler.target(), width, height, xoffset, yoffset);
	return handler.targetSrc();
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
    public AudioSampleInfo roolbackAudioSampleProcessing(UIObject object) throws IOException {
	MediaFileHandler handler = new MediaFileHandler(object, "sample.mp3");
	handler.roolback();
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
