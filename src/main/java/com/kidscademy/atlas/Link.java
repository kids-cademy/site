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
    private String domain;
    private String display;
    private String iconName;
    private String features;

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
     * @param display
     * @param iconName
     */
    public Link(URL url, String display, MediaSRC iconSrc) {
	this.url = url;
	this.domain = domain(url);
	this.display = display;
	this.iconSrc = iconSrc;
	this.features = "description";
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

    public String getDomain() {
	return domain;
    }

    public String getDisplay() {
	return display;
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

    public String getFeatures() {
	return features;
    }

    @Override
    public String toString() {
	return url != null ? url.toExternalForm() : "null";
    }

    private static final Map<String, String[]> DOMAINS = new HashMap<>();
    static {
	DOMAINS.put("wikipedia.org", new String[] { "Wikipedia", "description" });
	DOMAINS.put("softschools.com", new String[] { "Soft Schools", "description,facts" });
	DOMAINS.put("kiddle.co", new String[] { "Kiddle", "description" });
	DOMAINS.put("kids-cademy.com", new String[] { "kids (a)cademy", "description" });
    }

    public static Link create(URL url) {
	String domain = domain(url);

	Link link = new Link();
	link.url = url;
	link.domain = domain;

	link.display = DOMAINS.get(domain)[0];
	if (link.display == null) {
	    log.warn("Not registered display name for base doamin |%s|.", domain);
	    link.display = domain;
	}

	link.iconName = Strings.concat(basedomain(url), ".png");
	link.iconSrc = Files.linkSrc(link.iconName);
	link.features = DOMAINS.get(domain)[1];
	return link;
    }

    private static final Pattern DOMAIN_PATTERN = Pattern.compile("^(?:[^.]+\\.)*([^.]+\\.[^.]+)$");

    private static String domain(URL url) {
	Matcher matcher = DOMAIN_PATTERN.matcher(url.getHost());
	matcher.find();
	return matcher.group(1);
    }

    private static final Pattern BASEDOMAIN_PATTERN = Pattern.compile("^(?:[^.]+\\.)*([^.]+)\\..+$");

    private static String basedomain(URL url) {
	Matcher matcher = BASEDOMAIN_PATTERN.matcher(url.getHost());
	matcher.find();
	return matcher.group(1);
    }
}
