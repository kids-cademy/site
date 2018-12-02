package com.kidscademy.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Excludes
{
  private List<String> excludes;

  public Excludes()
  {
    excludes = new ArrayList<String>(0);
  }

  public Excludes(String[] excludes)
  {
    this.excludes = Arrays.asList(excludes);
  }

  public void add(String exclude)
  {
    if(!excludes.contains(exclude)) {
      excludes.add(exclude);
    }
  }

  public boolean reject(String path)
  {
    for(String exclude : excludes) {
      if(exclude.charAt(0) == '*') {
        if(path.endsWith(exclude.substring(1))) {
          return true;
        }
      }
      else {
        if(path.equals(exclude)) {
          return true;
        }
      }
    }
    return false;
  }
}