package com.kidscademy.atlas;

import java.io.Serializable;
import java.util.Collections;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;

import com.kidscademy.impl.MediaFileHandler;
import com.kidscademy.media.AudioSampleInfo;

@Entity
public class Instrument extends AtlasObject implements Serializable
{
  private static final long serialVersionUID = -3922363759020705548L;

  private Category category;
  private String sampleTitle;
  private String samplePath;
  private String waveformPath;

  @Transient
  private AudioSampleInfo sampleInfo;

  @Embedded
  @AttributeOverrides(
  {
      @AttributeOverride(name = "value", column = @Column(name = "date_value")), //
      @AttributeOverride(name = "mask", column = @Column(name = "date_mask"))
  })
  private HDate date;

  public Instrument()
  {
    dtype = getClass().getSimpleName();
  }

  @Transient
  private String pictureSrc;
  @Transient
  private String iconSrc;
  @Transient
  private String thumbnailSrc;
  @Transient
  private String sampleSrc;
  @Transient
  private String waveformSrc;

  @PrePersist
  @PreUpdate
  public void preSave()
  {
    picturePath = file(pictureSrc);
    iconPath = file(iconSrc);
    thumbnailPath = file(thumbnailSrc);
    samplePath = file(sampleSrc);
    waveformPath = file(waveformSrc);
  }

  private String file(String path)
  {
    return path != null ? path.substring(path.lastIndexOf('/') + 1) : null;
  }

  @PostLoad
  public void postLoad()
  {
    // database contains only media file names; convert to root relative URLs
    pictureSrc = MediaFileHandler.path(name, picturePath);
    iconSrc = MediaFileHandler.path(name, iconPath);
    thumbnailSrc = MediaFileHandler.path(name, thumbnailPath);
    sampleSrc = MediaFileHandler.path(name, samplePath);
    waveformSrc = MediaFileHandler.path(name, waveformPath);
  }

  public Category getCategory()
  {
    return category;
  }

  public void setCategory(Category category)
  {
    this.category = category;
  }

  public String getSampleTitle()
  {
    return sampleTitle;
  }

  public void setSampleTitle(String sampleTitle)
  {
    this.sampleTitle = sampleTitle;
  }

  public String getSamplePath()
  {
    return samplePath;
  }

  public void setSamplePath(String samplePath)
  {
    this.samplePath = samplePath;
  }

  public String getWaveformPath()
  {
    return waveformPath;
  }

  public void setWaveformPath(String waveformPath)
  {
    this.waveformPath = waveformPath;
  }

  public void setSampleInfo(AudioSampleInfo sampleInfo)
  {
    this.sampleInfo = sampleInfo;
  }

  public HDate getDate()
  {
    return date;
  }

  public void setDate(HDate date)
  {
    this.date = date;
  }

  @Override
  public String toString()
  {
    // toString is used by Audit to print instrument; leave it as it is
    return name;
  }

  public enum Category
  {
    // ENUM('PERCUSSION','STRINGS','WOODWIND','KEYBOARD')
    /** Instrument is sounded by being struck or scraped by a beater. */
    PERCUSSION,
    /** Produce sound from vibrating strings transmitted to the body of the instrument. */
    STRINGS,
    /** Produce sound by directing a focused stream of air across the edge of a hole in a cylindrical tube. */
    WOODWIND,
    /** A keyboard instrument is a musical instrument played using a keyboard. */
    KEYBOARD
  }

  public static Instrument create(User user)
  {
    Instrument instrument = new Instrument();
    instrument.dtype = Instrument.class.getSimpleName();
    instrument.user = user;
    instrument.state = AtlasObject.State.DEVELOPMENT;
    instrument.aliases = Collections.emptyList();
    instrument.spreading = Collections.emptyList();
    instrument.facts = Collections.emptyMap();
    instrument.links = Collections.emptyList();
    instrument.related = Collections.emptyList();
    return instrument;
  }
}
