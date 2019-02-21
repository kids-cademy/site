package com.kidscademy.atlas;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Embeddable;
import javax.persistence.Transient;

import com.kidscademy.util.Files;

import js.log.Log;
import js.log.LogFactory;
import js.util.Strings;

@Embeddable
public class Link {
    private static final Log log = LogFactory.getLog(Link.class);

    private int id;
    private URL url;
    private String name;
    private String iconName;

    /** Root-relative media SRC for link icon. */
    @Transient
    private MediaSRC iconSrc;

    public Link() {
    }

    /**
     * Test constructor.
     * 
     * @param objectId
     * @param url
     * @param name
     * @param iconName
     */
    public Link(URL url, String name, MediaSRC iconSrc) {
	this.url = url;
	this.name = name;
	this.iconSrc = iconSrc;
    }

    public void setIconName(String iconName) {
	this.iconName = iconName;
    }

    public void setIconSrc(MediaSRC iconSrc) {
	this.iconSrc = iconSrc;
    }

    public int getId() {
	return id;
    }

    public URL getUrl() {
	return url;
    }

    public String getName() {
	return name;
    }

    public String getIconName() {
	return iconName;
    }

    public MediaSRC getIconSrc() {
	return iconSrc;
    }

    public String getFileName() {
	String path = url.getPath();
	int lastPathseparator = path.lastIndexOf('/') + 1;
	return path.substring(lastPathseparator);
    }

    @Override
    public String toString() {
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

    public static Link create(URL url) {
	Matcher matcher = DOMAIN_PATTERN.matcher(url.getHost());
	matcher.find();
	String basedomain = matcher.group(1);

	Link link = new Link();
	link.url = url;

	link.name = DOMAIN_NAMES.get(basedomain);
	if (link.name == null) {
	    log.warn("Not registered display name for base doamin |%s|.", basedomain);
	    link.name = basedomain;
	}

	link.iconName = Strings.concat(basedomain, ".png");
	link.iconSrc = Files.linkSrc(link.iconName);
	return link;
    }
}
