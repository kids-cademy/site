package com.kidscademy.atlas;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.kidscademy.util.Files;

@Entity
@Table(name = "atlasobject")
public class UIObject implements MediaWrapper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String dtype;

    private String name;
    private String display;
    /**
     * Media file name for object icon. Object icon has a small dimension and has
     * 1:1 ratio; usually is 96x96 pixels. See {@link #pictureName} for details
     * about media file name.
     */
    private String iconName;

    @Transient
    private MediaSRC iconSrc;

    public UIObject() {
    }

    /**
     * Constructor for DTO projection.
     * 
     * @param id
     * @param dtype
     * @param name
     * @param display
     * @param iconName
     */
    public UIObject(int id, String dtype, String name, String display, String iconName) {
	this.id = id;
	this.dtype = dtype;
	this.name = name;
	this.display = display;
	this.iconName = iconName;
	this.iconSrc = Files.mediaSrc(dtype, name, iconName);
    }

    /**
     * Test constructor.
     * 
     * @param dtype
     * @param name
     */
    public UIObject(String dtype, String name) {
	this.dtype = dtype;
	this.name = name;
    }

    @PostLoad
    public void postLoad() {
	iconSrc = Files.mediaSrc(this, iconName);
    }

    public int getId() {
	return id;
    }

    public String getDtype() {
	return dtype;
    }

    public String getName() {
	return name;
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

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + id;
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	UIObject other = (UIObject) obj;
	if (id != other.id)
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return display;
    }
}
