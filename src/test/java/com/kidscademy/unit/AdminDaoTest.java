package com.kidscademy.unit;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.kidscademy.atlas.AtlasObject;
import com.kidscademy.atlas.Bird;
import com.kidscademy.atlas.HDate;
import com.kidscademy.atlas.Instrument;
import com.kidscademy.atlas.Instrument.Category;
import com.kidscademy.atlas.Link;
import com.kidscademy.atlas.Login;
import com.kidscademy.atlas.Region;
import com.kidscademy.atlas.Related;
import com.kidscademy.atlas.User;
import com.kidscademy.dao.AdminDao;
import com.kidscademy.dao.AdminDaoImpl;

import js.json.Json;
import js.transaction.TransactionFactory;
import js.transaction.eclipselink.TransactionFactoryImpl;
import js.unit.db.Database;
import js.util.Classes;
import js.util.Strings;

public class AdminDaoTest
{
  private static Database database;
  private static TransactionFactory factory;

  @BeforeClass
  public static void beforeClass() throws IOException
  {
    database = new Database("kids_cademy_test", "kids_cademy", "kids_cademy");
    factory = new TransactionFactoryImpl();
  }

  private AdminDao dao;

  @Before
  public void beforeTest() throws SQLException
  {
    database.load(Classes.getResourceAsStream("admin-data-set.xml"));
    dao = factory.newInstance(AdminDaoImpl.class);
  }

  @Test
  public void getUser()
  {
    Login login = new Login("john.doe@email.com", "secret");
    User user = dao.getUser(login);

    assertThat(user, notNullValue());
    assertThat(user.getId(), not(equalTo(0)));
    assertThat(user.getEmailAddress(), equalTo(login.getEmailAddress()));
    assertThat(user.getName(), equalTo(login.getEmailAddress()));
    assertThat(user.getPassword(), equalTo(login.getPassword()));
  }

  @Test
  public void getInstrument() throws MalformedURLException
  {
    Instrument instrument = dao.getInstrument(1);
    assertInstrument(instrument);
  }

  /** When create new instrument with not null media source values, media files fields should initialized before actual database persist. */
  @Test
  public void prePersistInstrument()
  {
    Instrument instrument = new Instrument();
    instrument.setUser(new User(1));
    instrument.setState(AtlasObject.State.DEVELOPMENT);
    instrument.setRank(9999);
    instrument.setCategory(Instrument.Category.STRINGS);
    instrument.setName("banjo");

    instrument.setIconSrc(path("banjo", "icon.jpg"));
    instrument.setThumbnailSrc(path("banjo", "thumbnail.png"));
    instrument.setPictureSrc(path("banjo", "picture.jpg"));
    instrument.setSampleSrc(path("banjo", "sample.mp3"));
    instrument.setWaveformSrc(path("banjo", "waveform.png"));

    dao.saveInstrument(instrument);

    Instrument persistedInstrument = dao.getInstrument(instrument.getId());
    assertThat(persistedInstrument.getIconFile(), equalTo("icon.jpg"));
    assertThat(persistedInstrument.getThumbnailFile(), equalTo("thumbnail.png"));
    assertThat(persistedInstrument.getPictureFile(), equalTo("picture.jpg"));
    assertThat(persistedInstrument.getSampleFile(), equalTo("sample.mp3"));
    assertThat(persistedInstrument.getWaveformFile(), equalTo("waveform.png"));
  }

  @Test
  public void preUpdateInstrument()
  {
    Instrument instrument = new Instrument();
    instrument.setUser(new User(1));
    instrument.setState(AtlasObject.State.DEVELOPMENT);
    instrument.setRank(9999);
    instrument.setCategory(Instrument.Category.STRINGS);
    instrument.setName("banjo");
    dao.saveInstrument(instrument);
    System.out.println("==================================================================");
    
    Instrument persistedInstrument = dao.getInstrument(instrument.getId());
    assertThat(persistedInstrument.getIconFile(), nullValue());
    assertThat(persistedInstrument.getThumbnailFile(), nullValue());
    assertThat(persistedInstrument.getPictureFile(), nullValue());
    assertThat(persistedInstrument.getSampleFile(), nullValue());
    assertThat(persistedInstrument.getWaveformFile(), nullValue());

    instrument.setIconSrc(path("banjo", "icon.jpg"));
    instrument.setThumbnailSrc(path("banjo", "thumbnail.png"));
    instrument.setPictureSrc(path("banjo", "picture.jpg"));
    instrument.setSampleSrc(path("banjo", "sample.mp3"));
    instrument.setWaveformSrc(path("banjo", "waveform.png"));
    // database merge is triggered on DAO because instrument ID is not zero
    assertThat(instrument.getId(), not(equalTo(0)));
//    instrument.preSave();
//    instrument.setIconFile("xxxxxxxxx");
//    instrument.setDisplay("Banjo Display");
    dao.saveInstrument(instrument);

    persistedInstrument = dao.getInstrument(instrument.getId());
    assertThat(persistedInstrument.getIconFile(), equalTo("icon.jpg"));
    assertThat(persistedInstrument.getThumbnailFile(), equalTo("thumbnail.png"));
    assertThat(persistedInstrument.getPictureFile(), equalTo("picture.jpg"));
    assertThat(persistedInstrument.getSampleFile(), equalTo("sample.mp3"));
    assertThat(persistedInstrument.getWaveformFile(), equalTo("waveform.png"));
  }

  @Test
  public void postLoadInstrument()
  {
    Instrument instrument = dao.getInstrument(1);
    assertThat(instrument.getIconSrc(), equalTo(path("icon.jpg")));
    assertThat(instrument.getThumbnailSrc(), equalTo(path("thumbnail.png")));
    assertThat(instrument.getPictureSrc(), equalTo(path("picture.jpg")));
    assertThat(instrument.getSampleSrc(), equalTo(path("sample.mp3")));
    assertThat(instrument.getWaveformSrc(), equalTo(path("waveform.png")));
  }

  @Test
  public void findObjectByType_Instrument() throws MalformedURLException
  {
    List<Instrument> instruments = dao.findObjectByType(Instrument.class);

    assertThat(instruments, notNullValue());
    assertThat(instruments, not(empty()));
    assertThat(instruments, hasSize(1));

    assertInstrument(instruments.get(0));
  }

  private static void assertInstrument(Instrument instrument) throws MalformedURLException
  {
    assertThat(instrument, notNullValue());
    assertThat(instrument.getId(), equalTo(1));
    assertThat(instrument.getUser(), notNullValue());
    assertThat(instrument.getUser().getId(), equalTo(1));
    assertThat(instrument.getUser().getEmailAddress(), equalTo("john.doe@email.com"));
    assertThat(instrument.getUser().getPassword(), equalTo("secret"));
    assertThat(instrument.getRank(), equalTo(1234));
    assertThat(instrument.getName(), equalTo("accordion"));
    assertThat(instrument.getName(), equalTo("accordion"));
    assertThat(instrument.getDisplay(), equalTo("Accordion"));
    assertThat(instrument.getDescription(), equalTo("Accordion description."));

    assertThat(instrument.getIconFile(), equalTo("icon.jpg"));
    assertThat(instrument.getThumbnailFile(), equalTo("thumbnail.png"));
    assertThat(instrument.getPictureFile(), equalTo("picture.jpg"));
    assertThat(instrument.getIconSrc(), equalTo(path("icon.jpg")));
    assertThat(instrument.getThumbnailSrc(), equalTo(path("thumbnail.png")));
    assertThat(instrument.getPictureSrc(), equalTo(path("picture.jpg")));

    assertThat(instrument.getCategory(), equalTo(Category.KEYBOARD));
    assertThat(instrument.getSampleTitle(), equalTo("Sample"));
    assertThat(instrument.getSampleFile(), equalTo("sample.mp3"));
    assertThat(instrument.getWaveformFile(), equalTo("waveform.png"));
    assertThat(instrument.getSampleSrc(), equalTo(path("sample.mp3")));
    assertThat(instrument.getWaveformSrc(), equalTo(path("waveform.png")));

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
    assertThat(link.getName(), equalTo("Wikipedia"));
    assertThat(link.getIconPath(), equalTo("icon.png"));
    assertThat(link.getUrl(), equalTo(new URL("https://en.wikipedia.org/wiki/Accordion")));

    assertThat(instrument.getRelated(), notNullValue());
    assertThat(instrument.getRelated(), not(empty()));
    assertThat(instrument.getRelated(), hasSize(2));
    // assertThat(instrument.getRelated().get(0), equalTo("bandoneon"));
    // assertThat(instrument.getRelated().get(1), equalTo("cimbalom"));
  }

  @Test
  public void serializeInstrument()
  {
    Instrument instrument = dao.getInstrument(1);

    Json json = Classes.loadService(Json.class);
    String value = json.stringify(instrument);
    System.out.println(value);
  }

  @Test
  public void saveInstrument() throws MalformedURLException
  {
    Instrument instrument = new Instrument();
    instrument.setUser(new User(1));
    instrument.setState(AtlasObject.State.DEVELOPMENT);
    instrument.setRank(9999);
    instrument.setCategory(Instrument.Category.STRINGS);
    instrument.setName("banjo");
    instrument.setDisplay("Banjo");
    instrument.setDescription("Banjo description.");
    instrument.setIconFile("icon.png");
    instrument.setThumbnailFile("thumbnail.png");
    instrument.setPictureFile("picture.jpg");
    instrument.setSampleTitle("Banjo solo");
    instrument.setSampleFile("sample.mp3");
    instrument.setWaveformFile("waveform.png");
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
    links.add(new Link(new URL("https://en.wikipedia.org/wiki/AccordionXXX"), "Wikipedia-xxx", "wikipedia.png"));
    links.add(new Link(new URL("http://en.wikipedia.org:443/wiki/Accordion"), "Wikipedia-www", "wikipedia.png"));
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
  public void getBird()
  {
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

  // ----------------------------------------------------------------------------------------------

  private static String path(String mediaFile)
  {
    return path("accordion", mediaFile);
  }

  private static String path(String objectName, String mediaFile)
  {
    return Strings.concat("/media/atlas/instrument/", objectName, '/', mediaFile);
  }
}
