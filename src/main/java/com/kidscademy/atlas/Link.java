package com.kidscademy.atlas;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Embeddable;

import js.log.Log;
import js.log.LogFactory;
import js.util.Strings;

@Embeddable
public class Link
{
  private static final Log log = LogFactory.getLog(Link.class);

  private int id;
  private URL url;
  private String name;
  private String iconPath;

  public Link()
  {
  }

  /**
   * Test constructor.
   * 
   * @param objectId
   * @param url
   * @param name
   * @param iconPath
   */
  public Link(URL url, String name, String iconPath)
  {
    this.url = url;
    this.name = name;
    this.iconPath = iconPath;
  }

  public void setIconPath(String iconPath)
  {
    this.iconPath = iconPath;
  }

  public int getId()
  {
    return id;
  }

  public URL getUrl()
  {
    return url;
  }

  public String getName()
  {
    return name;
  }

  public String getIconPath()
  {
    return iconPath;
  }

  @Override
  public String toString()
  {
    return url != null ? url.toExternalForm() : "null";
  }

  private static final Pattern DOMAIN_PATTERN = Pattern.compile("^(?:[^.]+\\.)*([^.]+)\\..+$");

  private static final Map<String, String> DOMAIN_NAMES = new HashMap<>();
  static {
    DOMAIN_NAMES.put("wikipedia", "Wikipedia");
    DOMAIN_NAMES.put("softschools", "Soft Schools");
    DOMAIN_NAMES.put("kiddle", "Kiddle");
    DOMAIN_NAMES.put("kids-cademy", "kids (a)cademy");
  }

  public static Link create(URL url)
  {
    Matcher matcher = DOMAIN_PATTERN.matcher(url.getHost());
    matcher.find();
    String basedomain = matcher.group(1);

    Link link = new Link();
    link.url = url;

    link.name = DOMAIN_NAMES.get(basedomain);
    if(link.name == null) {
      log.warn("Not registered display name for base doamin |%s|.", basedomain);
      link.name = basedomain;
    }

    link.iconPath = Strings.concat("link/", basedomain, ".png");
    return link;
  }
}
