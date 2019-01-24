package com.kidscademy.atlas;

import java.io.Serializable;
import java.util.Collections;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.Transient;

import com.kidscademy.media.AudioSampleInfo;
import com.kidscademy.util.Files;

/**
 * Musical instrument atlas object.
 * 
 * @author Iulian Rotaru
 */
@Entity
@DiscriminatorValue("instrument")
public class Instrument extends AtlasObject implements Serializable
{
  private static final long serialVersionUID = -3922363759020705548L;

  private Category category;
  private String sampleTitle;
  private String sampleFile;
  private String waveformFile;

  @Embedded
  @AttributeOverrides(
  {
      @AttributeOverride(name = "value", column = @Column(name = "date_value")), //
      @AttributeOverride(name = "mask", column = @Column(name = "date_mask"))
  })
  private HDate date;

  @Transient
  private AudioSampleInfo sampleInfo;
  /**
   * Root-relative media SRC for object picture. It contains root-relative path from repository context to media file and always starts with path separator,
   * e.g. <code>/media/atlas/instrument/accordion/picture.jpg</code>.
   * <p>
   * This field is used by user interface to actually render media file and is not persisted. It is initialized by {@link #postLoad()} immediately after this
   * instrument instance loaded from persistence. When instrument entity is saved to persistence this field is used to initialize related media file, see
   * {@link #postMerge(Instrument)}.
   */
  @Transient
  private String pictureSrc;
  /** Root-relative media SRC for object icon. See {@link #pictureSrc}. */
  @Transient
  private String iconSrc;
  /** Root-relative media SRC for object thumbnail. See {@link #pictureSrc}. */
  @Transient
  private String thumbnailSrc;
  /** Root-relative media SRC for object audio sample. See {@link #pictureSrc}. */
  @Transient
  private String sampleSrc;
  /** Root-relative media SRC for object audio waveform. See {@link #pictureSrc}. */
  @Transient
  private String waveformSrc;

  public Instrument()
  {
    dtype = getClass().getSimpleName().toLowerCase();
  }

  @PrePersist
  public void preSave()
  {
    postMerge(this);
  }

  /**
   * Hook executed after {@link EntityManager.merge()} to initialize media files from related root-relative SRC. This method is executed into a persistent
   * context while this instrument instance is managed, aka attached to context. Source instrument has media SRC properly initialized; it is not attached to
   * persistence context.
   * <p>
   * See {@link Instrument} for rationales and flow description.
   * 
   * @param source unmanaged source instrument.
   */
  public void postMerge(Instrument source)
  {
    pictureFile = Files.mediaFile(source.pictureSrc);
    iconFile = Files.mediaFile(source.iconSrc);
    thumbnailFile = Files.mediaFile(source.thumbnailSrc);
    sampleFile = Files.mediaFile(source.sampleSrc);
    waveformFile = Files.mediaFile(source.waveformSrc);
  }

  @PostLoad
  public void postLoad()
  {
    // database contains only media file names; convert to root relative URLs
    pictureSrc = Files.mediaSrc(this, pictureFile);
    iconSrc = Files.mediaSrc(this, iconFile);
    thumbnailSrc = Files.mediaSrc(this, thumbnailFile);
    sampleSrc = Files.mediaSrc(this, sampleFile);
    waveformSrc = Files.mediaSrc(this, waveformFile);
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

  public String getSampleFile()
  {
    return sampleFile;
  }

  public void setSampleFile(String samplePath)
  {
    this.sampleFile = samplePath;
  }

  public String getWaveformFile()
  {
    return waveformFile;
  }

  public void setWaveformFile(String waveformPath)
  {
    this.waveformFile = waveformPath;
  }

  public String getPictureSrc()
  {
    return pictureSrc;
  }

  public void setPictureSrc(String pictureSrc)
  {
    this.pictureSrc = pictureSrc;
  }

  public String getIconSrc()
  {
    return iconSrc;
  }

  public void setIconSrc(String iconSrc)
  {
    this.iconSrc = iconSrc;
  }

  public String getThumbnailSrc()
  {
    return thumbnailSrc;
  }

  public void setThumbnailSrc(String thumbnailSrc)
  {
    this.thumbnailSrc = thumbnailSrc;
  }

  public String getSampleSrc()
  {
    return sampleSrc;
  }

  public void setSampleSrc(String sampleSrc)
  {
    this.sampleSrc = sampleSrc;
  }

  public String getWaveformSrc()
  {
    return waveformSrc;
  }

  public void setWaveformSrc(String waveformSrc)
  {
    this.waveformSrc = waveformSrc;
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
