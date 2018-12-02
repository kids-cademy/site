package com.kidscademy.tool;

import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.kidscademy.atlas.Country;
import com.kidscademy.atlas.HDate;
import com.kidscademy.atlas.Instrument;
import com.kidscademy.atlas.Instrument.Category;
import com.kidscademy.atlas.Link;
import com.kidscademy.atlas.Region;
import com.kidscademy.dao.AtlasDao;
import com.kidscademy.dao.AtlasDaoImpl;

import js.json.Json;
import js.transaction.TransactionFactory;
import js.transaction.eclipselink.TransactionFactoryImpl;
import js.util.Classes;

public class ImportInstrument
{
  private static final File SOURCE_DIR = new File("D:\\docs\\workspaces\\kids-cademy\\data\\instruments\\build\\collection");

  public static void main(String... args) throws Exception
  {
    Json json = Classes.loadService(Json.class);

    TransactionFactory factory = new TransactionFactoryImpl();
    AtlasDao dao = factory.newInstance(AtlasDaoImpl.class);

    for(File objectDir : SOURCE_DIR.listFiles()) {
      File objectFile = new File(objectDir, "instrument_en.json");
      System.out.println(objectFile);

      InstrumentObject object = json.parse(new FileReader(objectFile), InstrumentObject.class);

      Instrument instrument = new Instrument();

      instrument.setRepositoryIndex(object.id);
      instrument.setRank(object.rank);
      instrument.setName(object.name);
      instrument.setDisplay(object.title);
      instrument.setDescription(object.description);
      instrument.setIconPath(object.iconPath);
      instrument.setThumbnailPath(object.thumbnailPath);
      instrument.setPicturePath(object.picturePath);

      instrument.setCategory(object.category);
      instrument.setSampleTitle(object.sampleTitle);
      instrument.setSamplePath(object.samplePath);
      instrument.setDate(new HDate(object.date.value, object.date.format, object.date.period, object.date.era));

      instrument.setAliases(Arrays.asList(object.aliases));

      List<Region> spreading = new ArrayList<>();
      for(Country country : object.spreading) {
        spreading.add(new Region(country.name()));
      }
      instrument.setSpreading(spreading);

      List<Link> links = new ArrayList<>();
      for(ExternalSource source : object.sources) {
        links.add(new Link(source.url, source.name, "Link description", source.icon.getName()));
      }
      instrument.setLinks(links);

      List<Integer> related = new ArrayList<>();
      for(int relatedIndex : object.related) {
        related.add(relatedIndex);
      }
      instrument.setRelated(related);

      instrument.setFacts(object.facts);

      dao.saveInstrument(instrument);
    }
  }

  private static class InstrumentObject
  {
    int id;
    int rank;
    String name;
    String title;
    String[] aliases;
    Category category;
    HDateSource date;
    String picturePath;
    String iconPath;
    String thumbnailPath;
    String description;
    String sampleTitle;
    Country[] spreading;
    Map<String, String> facts;
    int[] related;
    String samplePath;
    ExternalSource[] sources;
  }

  private static class HDateSource
  {
    long value;
    HDate.Format format;
    HDate.Period period;
    HDate.Era era;
  }

  private static class ExternalSource
  {
    String name;
    URL url;
    File icon;
  }
}
