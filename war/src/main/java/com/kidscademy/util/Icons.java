package com.kidscademy.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.kidscademy.atlas.Link;

import js.util.Strings;

public class Icons
{
  private static final Pattern DOMAIN_PATTERN = Pattern.compile("^(?:[^.]+\\.)*([^.]+)\\..+$");

  public static String getIconPath(Link link)
  {
    Matcher matcher = DOMAIN_PATTERN.matcher(link.getUrl().getHost());
    matcher.find();
    return Strings.concat("links/", matcher.group(1), ".png");
  }
}
