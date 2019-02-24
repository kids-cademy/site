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

    /** Database primary key. */
    private int id;
    /**
     * Link URL to external source document. That document should provide data about
     * advertised features, see {@link #features}.
     */
    private URL url;
    /**
     * URL base domain contains only domain and TLD. For example for
     * <code>www.softschools.com</code> links this domain value is
     * <code>softschools.com</code>.
     */
    private String domain;
    /**
     * Link name displayed on user interface. May be subject of
     * internationalization.
     */
    private String display;
    /**
     * Icon file name as stored into database. This value is used to set
     * {@link #iconSrc} when load link from database.
     */
    private String iconName;
    /**
     * Every link provides some kind of data, named features. This fields holds a
     * comma separated list of features and cannot be null or empty.
     * <p>
     * Current supported features are object <code>description</code> and
     * <code>facts</code> dictionary.
     */
    private String features;

    /**
     * Root-relative media SRC for link icon. This value is initialized when load
     * link from database with some contextual path and icon file name from
     * {@link #iconName}.
     */
    @Transient
    private MediaSRC iconSrc;

    public Link() {
    }

    /**
     * Test constructor. Initialize provided fields and features to hard coded
     * "description".
     * 
     * @param url
     *            URL for external source document.
     * @param display
     *            link name displayed on user interface,
     * @param iconName
     *            icon file name.
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

    /**
     * Convenient way to access path component of this link URL.
     * 
     * @return URL path component.
     * @see URL#getPath()
     */
    public String getPath() {
	return url.getPath();
    }

    /**
     * Convenient way to access file name from path component of this link URL.
     * Returned file name may include extension. It is the last path component, that
     * is, component after last path separator. If URL path ends with path separator
     * returns empty string.
     * 
     * @return file name from URL path, possible empty.
     */
    public String getFileName() {
	String path = url.getPath();
	int lastPathseparator = path.lastIndexOf('/') + 1;
	if (lastPathseparator >= path.length()) {
	    return "";
	}
	return path.substring(lastPathseparator);
    }

    /**
     * Return a comma separated list of features provided by this link.
     * 
     * @return this link features.
     * @see #features
     */
    public String getFeatures() {
	return features;
    }

    @Override
    public String toString() {
	return url != null ? url.toExternalForm() : "null";
    }

    /**
     * Currently supported domains should be declared here. Maybe to move to
     * external resource.
     */
    private static final Map<String, String[]> DOMAINS = new HashMap<>();
    static {
	DOMAINS.put("wikipedia.org", new String[] { "Wikipedia", "description" });
	DOMAINS.put("softschools.com", new String[] { "Soft Schools", "description,facts" });
	DOMAINS.put("kiddle.co", new String[] { "Kiddle", "description" });
	DOMAINS.put("kids-cademy.com", new String[] { "kids (a)cademy", "description,facts" });
    }

    /**
     * Factory method for link.
     * 
     * @param url
     *            URL of external source document.
     * @return newly created link.
     */
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

    /** Pattern for domain and TLD. */
    private static final Pattern DOMAIN_PATTERN = Pattern.compile("^(?:[^.]+\\.)*([^.]+\\.[^.]+)$");

    private static String domain(URL url) {
	Matcher matcher = DOMAIN_PATTERN.matcher(url.getHost());
	matcher.find();
	return matcher.group(1);
    }

    /** Pattern for domain without TLD. */
    private static final Pattern BASEDOMAIN_PATTERN = Pattern.compile("^(?:[^.]+\\.)*([^.]+)\\..+$");

    private static String basedomain(URL url) {
	Matcher matcher = BASEDOMAIN_PATTERN.matcher(url.getHost());
	matcher.find();
	return matcher.group(1);
    }
}
