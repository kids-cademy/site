package com.kidscademy.unit;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.kidscademy.atlas.AtlasObject;
import com.kidscademy.atlas.Bird;
import com.kidscademy.atlas.HDate;
import com.kidscademy.atlas.Instrument;
import com.kidscademy.atlas.Instrument.Category;
import com.kidscademy.atlas.Link;
import com.kidscademy.atlas.MediaSRC;
import com.kidscademy.atlas.Picture;
import com.kidscademy.atlas.Region;
import com.kidscademy.atlas.Related;
import com.kidscademy.atlas.UIObject;
import com.kidscademy.atlas.User;
import com.kidscademy.dao.AtlasDao;
import com.kidscademy.dao.AtlasDaoImpl;
import com.kidscademy.util.Files;

import js.json.Json;
import js.transaction.TransactionFactory;
import js.transaction.eclipselink.TransactionFactoryImpl;
import js.unit.db.Database;
import js.util.Classes;

public class AtlasDaoTest {
    private static Database database;
    private static TransactionFactory factory;

    @BeforeClass
    public static void beforeClass() throws IOException {
	database = new Database("kids_cademy_test", "kids_cademy", "kids_cademy");
	factory = new TransactionFactoryImpl();
    }

    private AtlasDao dao;

    @Before
    public void beforeTest() throws SQLException {
	database.load(Classes.getResourceAsStream("atlas-data-set.xml"));
	dao = factory.newInstance(AtlasDaoImpl.class);
    }

    @Test
    public void getInstrument() throws MalformedURLException {
	Instrument instrument = dao.getInstrument(1);
	assertInstrument(instrument);
    }

    /**
     * When create new instrument with not null media source values, media files
     * fields should initialized before actual database persist.
     */
    @Test
    public void prePersistInstrument() {
	Instrument instrument = new Instrument();
	instrument.setUser(new User(1));
	instrument.setState(AtlasObject.State.DEVELOPMENT);
	instrument.setRank(9999);
	instrument.setCategory(Instrument.Category.STRINGS);
	instrument.setName("banjo");
	instrument.setDisplay("Banjo");

	List<Picture> pictures = new ArrayList<>();
	instrument.setPictures(pictures);
	pictures.add(new Picture(src("banjo", "icon.jpg")));
	pictures.add(new Picture(src("banjo", "thumbnail.png")));
	pictures.add(new Picture(src("banjo", "picture.jpg")));

	instrument.setSampleSrc(src("banjo", "sample.mp3"));
	instrument.setWaveformSrc(src("banjo", "waveform.png"));

	dao.saveInstrument(instrument);

	Instrument persistedInstrument = dao.getInstrument(instrument.getId());

	assertThat(persistedInstrument.getPictures().get(0).getFileName(), equalTo("icon.jpg"));
	assertThat(persistedInstrument.getPictures().get(1).getFileName(), equalTo("thumbnail.png"));
	assertThat(persistedInstrument.getPictures().get(2).getFileName(), equalTo("picture.jpg"));

	assertThat(persistedInstrument.getSampleName(), equalTo("sample.mp3"));
	assertThat(persistedInstrument.getWaveformName(), equalTo("waveform.png"));
    }

    /**
     * Create an UI object that is detached from persistence context and set SRC for
     * all media files. On DAO save {@link EntityManager#merge()} is enacted. DAO
     * logic should invoke {@link Instrument#postMerge(Instrument)} and update media
     * file names from already set SRC values.
     */
    @Test
    public void postMergeInstrument() {
	Instrument uiInstrument = new Instrument();
	uiInstrument.setUser(new User(1));
	uiInstrument.setState(AtlasObject.State.DEVELOPMENT);
	uiInstrument.setRank(9999);
	uiInstrument.setCategory(Instrument.Category.STRINGS);
	uiInstrument.setName("banjo");
	uiInstrument.setDisplay("Banjo");

	List<Picture> pictures = new ArrayList<>();
	uiInstrument.setPictures(pictures);
	pictures.add(new Picture(src("banjo", "icon.jpg")));
	pictures.add(new Picture(src("banjo", "thumbnail.png")));
	pictures.add(new Picture(src("banjo", "picture.jpg")));

	dao.saveInstrument(uiInstrument);

	Instrument dbInstrument = dao.getInstrument(uiInstrument.getId());
	assertThat(dbInstrument.getSampleName(), nullValue());
	assertThat(dbInstrument.getWaveformName(), nullValue());

	uiInstrument.setSampleSrc(src("banjo", "sample.mp3"));
	uiInstrument.setWaveformSrc(src("banjo", "waveform.png"));
	// database merge is triggered on DAO because instrument ID is not zero
	assertThat(uiInstrument.getId(), not(equalTo(0)));
	dao.saveInstrument(uiInstrument);

	dbInstrument = dao.getInstrument(uiInstrument.getId());

	assertThat(dbInstrument.getPictures().get(0).getFileName(), equalTo("icon.jpg"));
	assertThat(dbInstrument.getPictures().get(1).getFileName(), equalTo("thumbnail.png"));
	assertThat(dbInstrument.getPictures().get(2).getFileName(), equalTo("picture.jpg"));

	assertThat(dbInstrument.getSampleName(), equalTo("sample.mp3"));
	assertThat(dbInstrument.getWaveformName(), equalTo("waveform.png"));
    }

    @Test
    public void postLoadInstrument() {
	Instrument instrument = dao.getInstrument(1);

	assertThat(instrument.getPictures(), notNullValue());
	assertThat(instrument.getPictures().size(), equalTo(4));
	assertThat(instrument.getPictures().get(0).getSrc(), equalTo(src("accordion", "icon.jpg")));
	assertThat(instrument.getPictures().get(1).getSrc(), equalTo(src("accordion", "piano-accordion.png")));
	assertThat(instrument.getPictures().get(2).getSrc(), equalTo(src("accordion", "button-accordion.png")));
	assertThat(instrument.getPictures().get(3).getSrc(), equalTo(src("accordion", "picture.jpg")));

	assertThat(instrument.getSampleSrc(), equalTo(src("accordion", "sample.mp3")));
	assertThat(instrument.getWaveformSrc(), equalTo(src("accordion", "waveform.png")));
    }

    @Test
    public void findObjectByType_Instrument() throws MalformedURLException {
	List<Instrument> instruments = dao.findObjectByType(Instrument.class);

	assertThat(instruments, notNullValue());
	assertThat(instruments, not(empty()));
	assertThat(instruments, hasSize(2));

	assertInstrument(instruments.get(0));
    }

    private static void assertInstrument(Instrument instrument) throws MalformedURLException {
	assertThat(instrument, notNullValue());
	assertThat(instrument.getId(), equalTo(1));
	assertThat(instrument.getUser(), notNullValue());
	assertThat(instrument.getUser().getId(), equalTo(1));
	assertThat(instrument.getUser().getEmailAddress(), equalTo("john.doe@email.com"));
	assertThat(instrument.getUser().getPassword(), equalTo("secret"));
	assertThat(instrument.getRank(), equalTo(1234));
	assertThat(instrument.getState(), equalTo(AtlasObject.State.DEVELOPMENT));
	assertThat(instrument.getName(), equalTo("accordion"));
	assertThat(instrument.getName(), equalTo("accordion"));
	assertThat(instrument.getDisplay(), equalTo("Accordion"));
	assertThat(instrument.getDescription(), equalTo("Accordion description."));

	assertThat(instrument.getPictures(), notNullValue());
	assertThat(instrument.getPictures().size(), equalTo(4));
	assertThat(instrument.getPictures().get(0).getFileName(), equalTo("icon.jpg"));
	assertThat(instrument.getPictures().get(0).getSrc(), equalTo(src("accordion", "icon.jpg")));
	assertThat(instrument.getPictures().get(1).getFileName(), equalTo("piano-accordion.png"));
	assertThat(instrument.getPictures().get(1).getSrc(), equalTo(src("accordion", "piano-accordion.png")));
	assertThat(instrument.getPictures().get(2).getFileName(), equalTo("button-accordion.png"));
	assertThat(instrument.getPictures().get(2).getSrc(), equalTo(src("accordion", "button-accordion.png")));
	assertThat(instrument.getPictures().get(3).getFileName(), equalTo("picture.jpg"));
	assertThat(instrument.getPictures().get(3).getSrc(), equalTo(src("accordion", "picture.jpg")));

	assertThat(instrument.getCategory(), equalTo(Category.KEYBOARD));
	assertThat(instrument.getSampleTitle(), equalTo("Sample"));
	assertThat(instrument.getSampleName(), equalTo("sample.mp3"));
	assertThat(instrument.getWaveformName(), equalTo("waveform.png"));
	assertThat(instrument.getSampleSrc(), equalTo(src("accordion", "sample.mp3")));
	assertThat(instrument.getWaveformSrc(), equalTo(src("accordion", "waveform.png")));

	assertThat(instrument.getDate().getValue(), equalTo(1234567890L));
	assertThat(instrument.getDate().getFormat(), equalTo(HDate.Format.DATE));
	assertThat(instrument.getDate().getPeriod(), equalTo(HDate.Period.FULL));
	assertThat(instrument.getDate().getEra(), equalTo(HDate.Era.CE));

	assertThat(instrument.getAliases(), notNullValue());
	assertThat(instrument.getAliases(), hasSize(1));
	assertThat(instrument.getAliases().get(0), equalTo("Squeezebox"));

	assertThat(instrument.getSpreading(), notNullValue());
	assertThat(instrument.getSpreading(), hasSize(1));
	assertThat(instrument.getSpreading().get(0), notNullValue());
	assertThat(instrument.getSpreading().get(0).getId(), equalTo(1));
	assertThat(instrument.getSpreading().get(0).getName(), equalTo("RO"));
	assertThat(instrument.getSpreading().get(0).getArea(), equalTo(Region.Area.CENTRAL));

	assertThat(instrument.getFacts(), notNullValue());
	assertThat(instrument.getFacts().keySet(), not(empty()));
	assertThat(instrument.getFacts().keySet(), hasSize(1));
	assertThat(instrument.getFacts().get("Fact #1"), equalTo("Fact #1 description."));

	assertThat(instrument.getLinks(), notNullValue());
	assertThat(instrument.getLinks(), hasSize(1));

	Link link = instrument.getLinks().get(0);
	assertThat(link, notNullValue());
	assertThat(link.getId(), equalTo(1));
	assertThat(link.getDisplay(), equalTo("Wikipedia"));
	assertThat(link.getIconName(), equalTo("wikipedia.png"));
	assertThat(link.getIconSrc(), equalTo(new MediaSRC("/media/link/wikipedia.png")));
	assertThat(link.getUrl(), equalTo(new URL("https://en.wikipedia.org/wiki/Accordion")));

	assertThat(instrument.getRelated(), notNullValue());
	assertThat(instrument.getRelated(), not(empty()));
	assertThat(instrument.getRelated(), hasSize(2));
	// assertThat(instrument.getRelated().get(0), equalTo("bandoneon"));
	// assertThat(instrument.getRelated().get(1), equalTo("cimbalom"));
    }

    @Test
    public void serializeInstrument() {
	Instrument instrument = dao.getInstrument(1);

	Json json = Classes.loadService(Json.class);
	String value = json.stringify(instrument);
	System.out.println(value);
    }

    @Test
    public void saveInstrument() throws MalformedURLException {
	Instrument instrument = new Instrument();
	instrument.setUser(new User(1));
	instrument.setState(AtlasObject.State.DEVELOPMENT);
	instrument.setRank(9999);
	instrument.setCategory(Instrument.Category.STRINGS);
	instrument.setName("banjo");
	instrument.setDisplay("Banjo");
	instrument.setDescription("Banjo description.");
	instrument.setSampleTitle("Banjo solo");
	instrument.setSampleName("sample.mp3");
	instrument.setWaveformName("waveform.png");
	instrument.setDate(new HDate(1821, HDate.Format.YEAR, HDate.Period.MIDDLE));

	List<String> aliases = new ArrayList<String>();
	aliases.add("Banjo Alias #1");
	aliases.add("Banjo Alias #2");
	instrument.setAliases(aliases);

	List<Region> spreading = new ArrayList<Region>();
	spreading.add(new Region("Europe", Region.Area.SOUTH));
	spreading.add(new Region("Africa", Region.Area.NORTH));
	instrument.setSpreading(spreading);

	List<Link> links = new ArrayList<>();
	links.add(new Link(new URL("https://en.wikipedia.org/wiki/AccordionXXX"), "Wikipedia-xxx",
		new MediaSRC("/media/link/wikipedia.png")));
	links.add(new Link(new URL("http://en.wikipedia.org:443/wiki/Accordion"), "Wikipedia-www",
		new MediaSRC("/media/link/wikipedia.png")));
	instrument.setLinks(links);

	Map<String, String> facts = new HashMap<>();
	facts.put("Banjo Fact #1", "Banjo fact #1 description.");
	facts.put("Banjo Fact #2", "Banjo fact #2 description.");
	instrument.setFacts(facts);

	List<Related> related = new ArrayList<>();
	related.add(new Related("accordion", 0.5F));
	instrument.setRelated(related);

	assertThat(instrument.getId(), equalTo(0));
	dao.saveInstrument(instrument);
	assertThat(instrument.getId(), not(equalTo(0)));
    }

    @Test
    public void getBird() {
	Bird bird = dao.getBird(2);

	assertThat(bird, notNullValue());
	assertThat(bird.getName(), equalTo("eagle"));
	assertThat(bird.getWingspan(), notNullValue());
	assertThat(bird.getWingspan().getMinimum(), equalTo(400));
	assertThat(bird.getWingspan().getMaximum(), equalTo(1200));

	assertThat(bird.getSpreading(), notNullValue());
	assertThat(bird.getSpreading(), empty());
	assertThat(bird.getSpreading(), hasSize(0));

	assertThat(bird.getFacts(), notNullValue());
	assertThat(bird.getFacts().keySet(), empty());
	assertThat(bird.getFacts().keySet(), hasSize(0));
    }

    @Test
    public void findObjectsByName() {
	List<UIObject> objects = dao.findObjectsByName(Instrument.class, Arrays.asList("accordion", "banjo"));
	assertThat(objects, notNullValue());
	assertThat(objects, hasSize(1));

	UIObject object = objects.get(0);
	assertThat(object, notNullValue());
	assertThat(object, not(instanceOf(Instrument.class)));
	assertThat(object.getId(), equalTo(1));
	assertThat(object.getDtype(), equalTo("instrument"));
	assertThat(object.getName(), equalTo("accordion"));
	assertThat(object.getDisplay(), equalTo("Accordion"));
	assertThat(object.getIconName(), equalTo("icon.jpg"));
	assertThat(object.getIconSrc(), equalTo(src("accordion", "icon_96x96.jpg")));
    }

    @Test
    public void getInstrumentsByCategory() {
	List<UIObject> objects = dao.getInstrumentsByCategory(Instrument.Category.KEYBOARD);
	assertThat(objects, notNullValue());
	assertThat(objects, hasSize(2));

	UIObject object = objects.get(0);
	assertThat(object, notNullValue());
	assertThat(object, not(instanceOf(Instrument.class)));
	assertThat(object.getId(), equalTo(1));
	assertThat(object.getDtype(), equalTo("instrument"));
	assertThat(object.getName(), equalTo("accordion"));
	assertThat(object.getDisplay(), equalTo("Accordion"));
	assertThat(object.getIconName(), equalTo("icon.jpg"));
	assertThat(object.getIconSrc(), equalTo(src("accordion", "icon_96x96.jpg")));
    }

    @Test
    public void getInstruments() {
	List<UIObject> objects = dao.getInstruments();
	assertThat(objects, notNullValue());
	assertThat(objects, hasSize(3));

	UIObject object = objects.get(0);
	assertThat(object, notNullValue());
	assertThat(object, not(instanceOf(Instrument.class)));
	assertThat(object.getId(), equalTo(1));
	assertThat(object.getDtype(), equalTo("instrument"));
	assertThat(object.getName(), equalTo("accordion"));
	assertThat(object.getDisplay(), equalTo("Accordion"));
	assertThat(object.getIconName(), equalTo("icon.jpg"));
	assertThat(object.getIconSrc(), equalTo(src("accordion", "icon_96x96.jpg")));
    }

    @Test
    public void objectState() {
	Instrument instrument = dao.getInstrument(1);
	assertThat(instrument.getState(), equalTo(AtlasObject.State.DEVELOPMENT));

	instrument = dao.getInstrument(3);
	assertThat(instrument.getState(), equalTo(AtlasObject.State.PUBLISHED));
    }

    @Test
    public void resetObjectSample() {
	dao.resetObjectSample("instrument", 1);

	Instrument instrument = dao.getInstrument(1);
	assertThat(instrument.getSampleTitle(), nullValue());
	assertThat(instrument.getSampleName(), nullValue());
	assertThat(instrument.getSampleSrc(), nullValue());
	assertThat(instrument.getWaveformName(), nullValue());
	assertThat(instrument.getWaveformSrc(), nullValue());
    }

    // ----------------------------------------------------------------------------------------------

    private static MediaSRC src(String objectName, String mediaFile) {
	return Files.mediaSrc("instrument", objectName, mediaFile);
    }
}
