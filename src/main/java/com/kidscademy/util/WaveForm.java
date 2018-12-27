package com.kidscademy.util;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class WaveForm implements WaveStream.SamplesListener, ProgressListener
{
  private static final String TYPE = "png";
  private static final Color BACKGROUND_COLOR = new Color(0xFF, 0xFF, 0xFF, 0x00);
  private static final Color WAVE_FORM_COLOR = new Color(0x00, 0x00, 0xFF);
 // private static final Color X_AXIS_COLOR = new Color(0x00, 0xFF, 0x00);

  private BufferedImage image;
  private Graphics2D graphics;
  private int width;
  private int height;
  private int x;
  private Double previousSample;
  private int decimationIndex;
  private int decimationBlockSize;
  private double maxSampleValue;

  public WaveForm(int width, int height)
  {
    this.width = width;
    this.height = height;
    this.image = new BufferedImage(this.width, this.height, BufferedImage.TYPE_4BYTE_ABGR);
    this.graphics = image.createGraphics();

    graphics.setBackground(BACKGROUND_COLOR);
    graphics.clearRect(0, 0, this.width, this.height);
    this.graphics.setColor(WAVE_FORM_COLOR);
  }

  public void setSamplesCount(long samplesCount)
  {
    this.decimationBlockSize = (int)(samplesCount / this.width);
  }

  @Override
  public void onWaveStreamSample(double sample)
  {
    if(this.decimationIndex < this.decimationBlockSize) {
      this.decimationIndex++;
      if(sample > this.maxSampleValue) {
        this.maxSampleValue = sample;
      }
      return;
    }

    if(this.previousSample != null) {
      sample = 1 - this.maxSampleValue;
      // GradientPaint gradient = new GradientPaint(0, 0, Color.yellow, this.width, this.height, Color.red);
      GradientPaint gradient = new GradientPaint(this.width, 0, Color.cyan, this.width, this.height, Color.blue);
      this.graphics.setPaint(gradient);
      drawLine(this.x, (int)(sample * this.height), this.x, this.height - (int)(sample * this.height));
      //drawLine(this.x, this.height, this.x, (int)(2 * sample * this.height));
      this.x++;
    }
    this.previousSample = sample;
    this.decimationIndex = 0;
    this.maxSampleValue = 0;
  }

  @Override
  public void onProgress(double percent)
  {
    System.out.println("progress " + percent);
  }

  private void drawLine(int fromX, int fromY, int toX, int toY)
  {
    Point2D.Double from = new Point2D.Double(fromX, fromY);
    Point2D.Double to = new Point2D.Double(toX, toY);
    Line2D line = new Line2D.Double(from, to);
    graphics.draw(line);
  }

  public void save(File file) throws IOException
  {
    //this.graphics.setColor(X_AXIS_COLOR);
    //drawLine(0, this.height / 2, width, this.height / 2);
    graphics.dispose();
    ImageIO.write(image, TYPE, file);
  }
}
