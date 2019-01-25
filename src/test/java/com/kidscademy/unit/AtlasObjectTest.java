package com.kidscademy.unit;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.kidscademy.atlas.HDate;
import com.kidscademy.atlas.Instrument;
import com.kidscademy.atlas.Link;
import com.kidscademy.atlas.Region;

import js.json.Json;
import js.util.Classes;

public class AtlasObjectTest
{
  @Test
  public void serializeJSON() throws MalformedURLException
  {
    Instrument instrument = new Instrument();
    instrument.setId(1);
    instrument.setRank(9999);
    instrument.setCategory(Instrument.Category.STRINGS);
    instrument.setName("banjo");
    instrument.setDisplay("Banjo");
    instrument.setDescription("Banjo description.");
    instrument.setIconName("icon.png");
    instrument.setThumbnailName("thumbnail.png");
    instrument.setPictureName("picture.jpg");
    instrument.setSampleTitle("Banjo solo");
    instrument.setSampleName("sample.mp3");
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

    Json json = Classes.loadService(Json.class);
    String value = json.stringify(instrument);
    System.out.println(value);
  }

  @Test
  public void deserializeJSON() throws Exception
  {
    Json json = Classes.loadService(Json.class);
    Instrument instrument = json.parse(Classes.getResourceAsReader("instrument.json"), Instrument.class);
    System.out.println(instrument);
  }
}
