package com.kidscademy.impl;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.im4java.process.ProcessStarter;

import com.kidscademy.AtlasService;
import com.kidscademy.CT;
import com.kidscademy.atlas.Instrument;
import com.kidscademy.atlas.Link;
import com.kidscademy.atlas.MediaSRC;
import com.kidscademy.atlas.MediaWrapper;
import com.kidscademy.atlas.UIObject;
import com.kidscademy.atlas.User;
import com.kidscademy.dao.AtlasDao;
import com.kidscademy.media.AudioProcessor;
import com.kidscademy.media.AudioSampleInfo;
import com.kidscademy.media.ImageProcessor;
import com.kidscademy.util.Files;

import js.core.AppContext;
import js.http.form.Form;
import js.lang.BugError;
import js.log.Log;
import js.log.LogFactory;

public class AtlasServiceImpl implements AtlasService {
    private static final Log log = LogFactory.getLog(AtlasServiceImpl.class);

    private final AppContext context;
    private final AtlasDao dao;
    private final AudioProcessor audio;
    private final ImageProcessor image;

    public AtlasServiceImpl(AppContext context, AtlasDao dao, AudioProcessor audio, ImageProcessor image) {
	log.trace("AtlasServiceImpl(AppContext, AtlasDao)");
	this.context = context;
	this.dao = dao;
	this.audio = audio;
	this.image = image;
	ProcessStarter.setGlobalSearchPath(CT.imageMagickPath());
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
    public int saveInstrument(Instrument instrument) {
	dao.saveInstrument(instrument);
	return instrument.getId();
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
    public MediaSRC uploadPictureFile(Form form) throws IOException {
	String collectionName = form.getValue("collection-name");
	String objectName = form.getValue("object-name");

	MediaSRC pictureSrc = Files.mediaSrc(collectionName, objectName, "picture.jpg");
	File pictureFile = Files.mediaFile(pictureSrc);
	pictureFile.getParentFile().mkdirs();

	image.saveObjectPicture(form.getUploadedFile("file").getFile(), pictureFile);
	return pictureSrc;
    }

    @Override
    public MediaSRC uploadThumbnailFile(Form form) throws IOException {
	String collectionName = form.getValue("collection-name");
	String objectName = form.getValue("object-name");

	MediaSRC thumbnailSrc = Files.mediaSrc(collectionName, objectName, "thumbnail.png");
	File thumbnailFile = Files.mediaFile(thumbnailSrc);
	thumbnailFile.getParentFile().mkdirs();

	image.saveObjectThumbnail(form.getUploadedFile("file").getFile(), thumbnailFile);
	return thumbnailSrc;
    }

    @Override
    public MediaSRC uploadIconFile(Form form) throws IOException {
	String collectionName = form.getValue("collection-name");
	String objectName = form.getValue("object-name");

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
    public Link createLink(URL url) {
	return Link.create(url);
    }

    // ----------------------------------------------------------------------------------------------
    // Object Audio Sample Services

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
	return getAudioSampleInfo(object, handler.target(), handler.targetSrc());
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
	audio.fadeIn(handler.source(), handler.target(), 2.5F);
	return getAudioSampleInfo(object, handler.target(), handler.targetSrc());
    }

    @Override
    public AudioSampleInfo fadeOutAudioSample(UIObject object) throws IOException {
	MediaFileHandler handler = new MediaFileHandler(object, "sample.mp3");
	audio.fadeOut(handler.source(), handler.target(), 2.5F);
	return getAudioSampleInfo(object, handler.target(), handler.targetSrc());
    }

    @Override
    public MediaSRC generateWaveform(UIObject object) throws IOException {
	File sampleFile = Files.mediaFile(object, "sample.mp3");
	if (!sampleFile.exists()) {
	    throw new BugError("Database not consistent. Missing sample file |%s|.", sampleFile);
	}
	return generateWaveform(object, sampleFile);
    }

    @Override
    public AudioSampleInfo undoAudioSampleProcessing(UIObject object) throws IOException {
	MediaFileHandler handler = new MediaFileHandler(object, "sample.mp3");
	handler.rollback();
	return getAudioSampleInfo(object, handler.source(), handler.sourceSrc());
    }

    @Override
    public AudioSampleInfo commitAudioSampleProcessing(UIObject object) throws IOException {
	MediaFileHandler handler = new MediaFileHandler(object, "sample.mp3");
	handler.commit();
	return getAudioSampleInfo(object, handler.source(), handler.sourceSrc());
    }

    @Override
    public void removeAudioSample(UIObject object) throws IOException {
	MediaFileHandler handler = new MediaFileHandler(object, "sample.mp3");
	handler.delete();
	dao.removeInstrumentSample(object.getName());
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
