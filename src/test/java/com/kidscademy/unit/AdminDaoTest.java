package com.kidscademy.unit;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
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

import com.kidscademy.atlas.Bird;
import com.kidscademy.atlas.HDate;
import com.kidscademy.atlas.Instrument;
import com.kidscademy.atlas.Instrument.Category;
import com.kidscademy.atlas.Link;
import com.kidscademy.atlas.Region;
import com.kidscademy.dao.AdminDao;
import com.kidscademy.dao.AdminDaoImpl;

import js.json.Json;
import js.transaction.TransactionFactory;
import js.transaction.eclipselink.TransactionFactoryImpl;
import js.unit.db.Database;
import js.util.Classes;

public class AdminDaoTest
{
  private static final String HIBERNATE_CFG = "hibernate.test.cfg.xml";

  private static Database database;
  private static TransactionFactory factory;

  @BeforeClass
  public static void beforeClass() throws IOException
  {
    database = new Database(HIBERNATE_CFG);
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
  public void getInstrument() throws MalformedURLException
  {
    Instrument instrument = dao.getInstrument(1);
    assertInstrument(instrument);
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
    assertThat(instrument.getRank(), equalTo(1234));
    assertThat(instrument.getName(), equalTo("accordion"));
    assertThat(instrument.getName(), equalTo("accordion"));
    assertThat(instrument.getDisplay(), equalTo("Accordion"));
    assertThat(instrument.getDescription(), equalTo("Accordion description."));
    assertThat(instrument.getIconPath(), equalTo("icon.jpg"));
    assertThat(instrument.getThumbnailPath(), equalTo("thumbnail.png"));
    assertThat(instrument.getPicturePath(), equalTo("picture.jpg"));

    assertThat(instrument.getCategory(), equalTo(Category.KEYBOARD));
    assertThat(instrument.getSampleTitle(), equalTo("Sample"));
    assertThat(instrument.getSamplePath(), equalTo("sample.mp3"));

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
    assertThat(link.getDescription(), equalTo("Wikipedia link."));
    assertThat(link.getIconPath(), equalTo("icon.png"));
    assertThat(link.getUrl(), equalTo(new URL("https://en.wikipedia.org/wiki/Accordion")));

    assertThat(instrument.getRelated(), notNullValue());
    assertThat(instrument.getRelated(), not(empty()));
    assertThat(instrument.getRelated(), hasSize(2));
    assertThat(instrument.getRelated().get(0), equalTo("bandoneon"));
    assertThat(instrument.getRelated().get(1), equalTo("cimbalom"));
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
    instrument.setRank(9999);
    instrument.setCategory(Instrument.Category.STRINGS);
    instrument.setName("banjo");
    instrument.setDisplay("Banjo");
    instrument.setDescription("Banjo description.");
    instrument.setIconPath("icon.png");
    instrument.setThumbnailPath("thumbnail.png");
    instrument.setPicturePath("picture.jpg");
    instrument.setSampleTitle("Banjo solo");
    instrument.setSamplePath("sample.mp3");
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
    links.add(new Link(1, new URL("https://en.wikipedia.org/wiki/AccordionXXX"), "Wikipedia-xxx", "Accordion article on wikipedia.", "wikipedia.png"));
    links.add(new Link(1, new URL("http://en.wikipedia.org:443/wiki/Accordion"), "Wikipedia-www", "Accordion article on wikipedia.", "wikipedia.png"));
    //instrument.setLinks(links);

    Map<String, String> facts = new HashMap<>();
    facts.put("Banjo Fact #1", "Banjo fact #1 description.");
    facts.put("Banjo Fact #2", "Banjo fact #2 description.");
    instrument.setFacts(facts);

    assertThat(instrument.getId(), not(equalTo(0)));
    dao.saveInstrument(instrument);
    assertThat(instrument.getId(), not(equalTo(0)));
  }

  public void saveInstrument_XP() throws MalformedURLException
  {
    Instrument instrument = new Instrument();
    List<Link> links = new ArrayList<>();

    System.out.println("===========================================================");

    // Instrument saved = dao.getInstrument(instrument.getId());
    // assertThat(saved.getIndex(), equalTo(instrument.getIndex()));
    // assertThat(saved.getRank(), equalTo(instrument.getRank()));
    // assertThat(saved.getCategory(), equalTo(instrument.getCategory()));
    // assertThat(saved.getName(), equalTo(instrument.getName()));
    // assertThat(saved.getDisplay(), equalTo(instrument.getDisplay()));
    // assertThat(saved.getDescription(), equalTo(instrument.getDescription()));
    // assertThat(saved.getIconPath(), equalTo(instrument.getIconPath()));
    // assertThat(saved.getThumbnailPath(), equalTo(instrument.getThumbnailPath()));
    // assertThat(saved.getPicturePath(), equalTo(instrument.getPicturePath()));
    // assertThat(saved.getSampleTitle(), equalTo(instrument.getSampleTitle()));
    // assertThat(saved.getSamplePath(), equalTo(instrument.getSamplePath()));

    Link link = new Link(1, new URL("https://en.wikipedia.org/wiki/Accordion"), "Wikipedia-1", "Accordion article on wikipedia.", "wikipedia.png");
    dao.saveLink(link);
    assertThat(link.getId(), not(equalTo(0)));

    links = new ArrayList<>();
    links.add(link);
    instrument.setLinks(links);

    dao.saveInstrument(instrument);

    System.out.println("===========================================================");

    instrument.setLinks(new ArrayList<Link>(0));
    dao.saveInstrument(instrument);

    // dao.saveLink(new Link(instrument, "Wikipedia-2", "Accordion article on wikipedia.", "wikipedia.png", new
    // URL("https://en.wikipedia.org/wiki/Accordion")));
    // dao.saveLink(new Link(instrument, "Wikipedia-3", "Accordion article on wikipedia.", "wikipedia.png", new
    // URL("https://en.wikipedia.org/wiki/Accordion")));
    // dao.saveLink(new Link(instrument, "Wikipedia-4", "Accordion article on wikipedia.", "wikipedia.png", new
    // URL("https://en.wikipedia.org/wiki/Accordion")));
    // dao.saveLink(new Link(instrument, "Wikipedia-5", "Accordion article on wikipedia.", "wikipedia.png", new
    // URL("https://en.wikipedia.org/wiki/Accordion")));
    // dao.saveLink(new Link(instrument, "Wikipedia-6", "Accordion article on wikipedia.", "wikipedia.png", new
    // URL("https://en.wikipedia.org/wiki/Accordion")));
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

  @Test
  public void saveLink() throws MalformedURLException
  {
    Link link = new Link(1, new URL("https://en.wikipedia.org/wiki/Piano"), "Wikipedia", "Piano article on wikipedia.", "wikipedia.png");
    dao.saveLink(link);
    assertThat(link.getId(), not(equalTo(0)));
  }
}
