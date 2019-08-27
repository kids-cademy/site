package com.kidscademy.tool;

import java.net.URL;

import com.kidscademy.atlas.HDate;
import com.kidscademy.atlas.Instrument.Category;

public class Descriptor {
    public String name;
    public int rank;
    public Category category;
    public HDate date;
    public Country[] spreading;
    public String[] related;
    public ExternalSource[] sources;
    public URL wikipediaArticle;
}
