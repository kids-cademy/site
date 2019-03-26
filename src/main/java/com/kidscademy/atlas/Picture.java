package com.kidscademy.atlas;

import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.kidscademy.util.Files;

@Entity
@Cacheable
public class Picture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    /** Picture name, unique per atlas object. */
    private String name;

    private String kind;

    private Date uploadDate;

    private String source = "";

    /**
     * Picture file name.
     * <p>
     * This fields contains only file name, including extension, but does not
     * contain any path components. This is to avoid keeping path structure into
     * database. Path components are added when object is loaded from persistence by
     * a specialized hook, from every concrete object class. Media file name plus
     * path components are a root-relative URL and is named media SRC; remember that
     * root-relative URL is the URL path after server that starts from context.
     */
    private String fileName;

    private int fileSize;

    private int width;

    private int height;

    @Transient
    private MediaSRC src;

    public Picture() {
    }

    /**
     * Test constructor.
     * 
     * @param src
     */
    public Picture(MediaSRC src) {
	this.src = src;
	this.kind = Files.basename(src.fileName());
	this.name = Files.basename(src.fileName());
	this.uploadDate = new Date();
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getKind() {
	return kind;
    }

    public void setKind(String kind) {
	this.kind = kind;
    }

    public Date getUploadDate() {
	return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
	this.uploadDate = uploadDate;
    }

    public String getSource() {
	return source;
    }

    public void setSource(String source) {
	this.source = source;
    }

    public String getFileName() {
	return fileName;
    }

    public void setFileName(String fileName) {
	this.fileName = fileName;
    }

    public int getFileSize() {
	return fileSize;
    }

    public void setFileSize(int fileSize) {
	this.fileSize = fileSize;
    }

    public int getWidth() {
	return width;
    }

    public void setWidth(int width) {
	this.width = width;
    }

    public int getHeight() {
	return height;
    }

    public void setHeight(int height) {
	this.height = height;
    }

    public void setSrc(MediaSRC src) {
	this.src = src;
    }

    public MediaSRC getSrc() {
	return src;
    }

    public void postLoad(AtlasObject object) {
	src = Files.mediaSrc(object, fileName);
    }

    public void postMerge(Picture source) {
	fileName = source.src != null ? source.src.fileName() : null;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((name == null) ? 0 : name.hashCode());
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
	Picture other = (Picture) obj;
	if (name == null) {
	    if (other.name != null)
		return false;
	} else if (!name.equals(other.name))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return name;
    }
}
