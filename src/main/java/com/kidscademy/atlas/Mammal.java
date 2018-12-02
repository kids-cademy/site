package com.kidscademy.atlas;

import java.util.List;
import java.util.Set;

public class Mammal
{
  private int id;
  private ScientificName scientificName;
  private String binomialName;
  private String commonName;
  private String iconPath;
  private List<Paragraph> description;
  private List<Picture> pictures;
  private List<Trait> traits;
  private List<Fact> facts;
  private ConservationStatus conservation;
  private Set<Link> links;
  private Set<MammalItem> related;
  
  private String range;
  private String habitat;
  private String diet;
  private Lifespan lifespan;
  private Length length;
  private Height height;
  private Mass mass;
}
