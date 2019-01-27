package com.kidscademy.impl;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.im4java.process.ProcessStarter;

import com.kidscademy.AtlasService;
import com.kidscademy.CT;
import com.kidscademy.atlas.GraphicObject;
import com.kidscademy.atlas.Instrument;
import com.kidscademy.atlas.Link;
import com.kidscademy.atlas.Login;
import com.kidscademy.atlas.MediaSRC;
import com.kidscademy.atlas.User;
import com.kidscademy.dao.AdminDao;
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
    private final AdminDao dao;
    private final AudioProcessor audio;
    private final ImageProcessor image;

    public AtlasServiceImpl(AppContext context, AdminDao dao, AudioProcessor audio, ImageProcessor image) {
	log.trace("AtlasServiceImpl(AppContext, AtlasDao)");
	this.context = context;
	this.dao = dao;
	this.audio = audio;
	this.image = image;
	ProcessStarter.setGlobalSearchPath(CT.imageMagickPath());
    }

    @Override
    public boolean login(Login login) {
	User user = dao.getUser(login);
	if (user != null) {
	    context.login(user);
	    return true;
	}
	return false;
    }

    @Override
    public boolean isAuthenticated() {
	return context.isAuthenticated();
    }

    @Override
    public List<Instrument> getInstruments() {
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
    public List<GraphicObject> getRelatedInstruments(List<String> names) {
	return dao.findObjectsByName(Instrument.class, names);
    }

    @Override
    public List<GraphicObject> getAvailableInstruments(Instrument.Category category, List<GraphicObject> related) {
	List<GraphicObject> instruments = dao.getInstrumentsByCategory(category);
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
	File pictureFile = Files.mediaFile(collectionName, objectName, "picture.jpg");

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
	String collectionName = form.getValue("collection-name");
	String objectName = form.getValue("object-name");

	MediaFileHandler handler = new MediaFileHandler(collectionName, objectName, "sample.mp3");
	handler.upload(form.getFile("file"));
	return getAudioSampleInfo(collectionName, objectName, handler.source(), handler.sourceSrc());
    }

    @Override
    public AudioSampleInfo normalizeSample(String collectionName, String objectName) throws IOException {
	MediaFileHandler handler = new MediaFileHandler(collectionName, objectName, "sample.mp3");
	audio.normalizeLevel(handler.source(), handler.target());
	return getAudioSampleInfo(collectionName, objectName, handler.target(), handler.targetSrc());
    }

    @Override
    public AudioSampleInfo convertToMono(String collectionName, String objectName) throws IOException {
	MediaFileHandler handler = new MediaFileHandler(collectionName, objectName, "sample.mp3");
	audio.convertToMono(handler.source(), handler.target());
	return getAudioSampleInfo(collectionName, objectName, handler.target(), handler.targetSrc());
    }

    @Override
    public AudioSampleInfo trimSilence(String collectionName, String objectName) throws IOException {
	MediaFileHandler handler = new MediaFileHandler(collectionName, objectName, "sample.mp3");
	audio.trimSilence(handler.source(), handler.target());
	return getAudioSampleInfo(collectionName, objectName, handler.target(), handler.targetSrc());
    }

    @Override
    public AudioSampleInfo cutAudioSample(String collectionName, String objectName, float start) throws IOException {
	MediaFileHandler handler = new MediaFileHandler(collectionName, objectName, "sample.mp3");
	audio.deleteSegment(handler.source(), handler.target(), start, start + 30);
	return getAudioSampleInfo(collectionName, objectName, handler.target(), handler.targetSrc());
    }

    @Override
    public AudioSampleInfo fadeInAudioSample(String collectionName, String objectName) throws IOException {
	MediaFileHandler handler = new MediaFileHandler(collectionName, objectName, "sample.mp3");
	audio.fadein(handler.source(), handler.target(), 2.5F);
	return getAudioSampleInfo(collectionName, objectName, handler.target(), handler.targetSrc());
    }

    @Override
    public AudioSampleInfo fadeOutAudioSample(String collectionName, String objectName) throws IOException {
	MediaFileHandler handler = new MediaFileHandler(collectionName, objectName, "sample.mp3");
	audio.fadeout(handler.source(), handler.target(), 2.5F);
	return getAudioSampleInfo(collectionName, objectName, handler.target(), handler.targetSrc());
    }

    @Override
    public MediaSRC generateWaveform(String collectionName, String objectName) throws IOException {
	File sampleFile = Files.mediaFile(collectionName, objectName, "sample.mp3");
	if (!sampleFile.exists()) {
	    throw new BugError("Database not consistent. Missing sample file |%s|.", sampleFile);
	}
	return generateWaveform(collectionName, objectName, sampleFile);
    }

    @Override
    public AudioSampleInfo undoMediaProcessing(String collectionName, String objectName) throws IOException {
	MediaFileHandler handler = new MediaFileHandler(collectionName, objectName, "sample.mp3");
	handler.rollback();
	return getAudioSampleInfo(collectionName, objectName, handler.source(), handler.sourceSrc());
    }

    @Override
    public AudioSampleInfo commitMediaProcessing(String collectionName, String objectName) throws IOException {
	MediaFileHandler handler = new MediaFileHandler(collectionName, objectName, "sample.mp3");
	handler.commit();
	return getAudioSampleInfo(collectionName, objectName, handler.source(), handler.sourceSrc());
    }

    @Override
    public void removeObjectSample(String collectionName, String objectName) throws IOException {
	MediaFileHandler handler = new MediaFileHandler(collectionName, objectName, "sample.mp3");
	handler.delete();
	dao.removeInstrumentSample(objectName);
	Files.mediaFile(collectionName, objectName, "sample.mp3").delete();
	Files.mediaFile(collectionName, objectName, "waveform.png").delete();
    }

    // ----------------------------------------------------------------------------------------------

    private AudioSampleInfo getAudioSampleInfo(String collectionName, String objectName, File file, MediaSRC mediaSrc)
	    throws IOException {
	AudioSampleInfo info = audio.getAudioFileInfo(file);
	info.setSampleSrc(mediaSrc);
	info.setWaveformSrc(generateWaveform(collectionName, objectName, file));
	return info;
    }

    private MediaSRC generateWaveform(String collectionName, String objectName, File audioFile) throws IOException {
	MediaSRC waveformSrc = Files.mediaSrc(collectionName, objectName, "waveform.png");
	File waveformFile = Files.mediaFile(waveformSrc);
	audio.generateWaveform(audioFile, waveformFile);
	return waveformSrc;
    }
}
