package com.kidscademy.model;

import java.util.List;

@SuppressWarnings("unused")
public class Feedback
{
  private Counters counters;
  private List<Suggestion> suggestions;

  public Feedback(Counters counters, List<Suggestion> suggestions)
  {
    this.counters = counters;
    this.suggestions = suggestions;
  }
}
