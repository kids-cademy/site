package com.kidscademy.tool;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import com.github.axet.vget.VGet;
import com.github.axet.vget.info.VGetParser;
import com.github.axet.vget.info.VideoFileInfo;
import com.github.axet.vget.info.VideoInfo;
import com.github.axet.vget.vhs.VimeoInfo;
import com.github.axet.vget.vhs.YouTubeInfo;
import com.github.axet.wget.SpeedInfo;
import com.github.axet.wget.info.DownloadInfo;
import com.github.axet.wget.info.DownloadInfo.Part;
import com.github.axet.wget.info.DownloadInfo.Part.States;

public class AppManagedDownload
{
  public static void main(String[] args) throws Exception
  {
    // String videoURL = "https://www.youtube.com/watch?v=VGPLQGTzeyk";

    String videoURL = "https://www.youtube.com/watch?v=TpJRBdaf9J8";
    File targetDir = new File("d:/tmp/youtube/");

    final AtomicBoolean stop = new AtomicBoolean(false);
    URL url = new URL(videoURL);

    VGetParser vgetParser = VGet.parser(url);
    VideoInfo videoinfo = vgetParser.info(url);
    VGet vget = new VGet(videoinfo, targetDir);

    VGetStatus vgetStatus = new VGetStatus(videoinfo);
    vget.extract(vgetParser, stop, vgetStatus);

    System.out.println("Title: " + videoinfo.getTitle());
    for(VideoFileInfo videoFileInfo : videoinfo.getInfo()) {
      videoFileInfo.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:64.0) Gecko/20100101 Firefox/64.0");
      System.out.println("Download URL: " + videoFileInfo.getSource());
    }

    vget.download(vgetParser, stop, vgetStatus);
  }

  private static class VGetStatus implements Runnable
  {
    private VideoInfo videoInfo;
    private long last;
    private Map<VideoFileInfo, SpeedInfo> fileSpeedInfo = new HashMap<>();

    public VGetStatus(VideoInfo videoInfo)
    {
      this.videoInfo = videoInfo;
    }

    public SpeedInfo getSpeedInfo(VideoFileInfo videoFileInfo)
    {
      SpeedInfo speedInfo = fileSpeedInfo.get(videoFileInfo);
      if(speedInfo == null) {
        speedInfo = new SpeedInfo();
        speedInfo.start(videoFileInfo.getCount());
        fileSpeedInfo.put(videoFileInfo, speedInfo);
      }
      return speedInfo;
    }

    @Override
    public void run()
    {
      List<VideoFileInfo> videoFilesInfo = videoInfo.getInfo();

      switch(videoInfo.getState()) {
      case EXTRACTING:
      case EXTRACTING_DONE:
      case DONE:
        if(videoInfo instanceof YouTubeInfo) {
          YouTubeInfo i = (YouTubeInfo)videoInfo;
          System.out.println(videoInfo.getState() + " " + i.getVideoQuality());
        }
        else if(videoInfo instanceof VimeoInfo) {
          VimeoInfo i = (VimeoInfo)videoInfo;
          System.out.println(videoInfo.getState() + " " + i.getVideoQuality());
        }
        else {
          System.out.println("downloading unknown quality");
        }
        for(VideoFileInfo d : videoInfo.getInfo()) {
          SpeedInfo speedInfo = getSpeedInfo(d);
          speedInfo.end(d.getCount());
          System.out.println(String.format("file:%d - %s (%s)", videoFilesInfo.indexOf(d), d.targetFile, formatSpeed(speedInfo.getAverageSpeed())));
        }
        break;

      case ERROR:
        System.out.println(videoInfo.getState() + " " + videoInfo.getDelay());
        if(videoFilesInfo != null) {
          for(DownloadInfo dinfo : videoFilesInfo) {
            System.out.println("file:" + videoFilesInfo.indexOf(dinfo) + " - " + dinfo.getException() + " delay:" + dinfo.getDelay());
          }
        }
        break;

      case RETRYING:
        System.out.println(videoInfo.getState() + " " + videoInfo.getDelay());
        if(videoFilesInfo != null) {
          for(DownloadInfo dinfo : videoFilesInfo) {
            System.out.println("file:" + videoFilesInfo.indexOf(dinfo) + " - " + dinfo.getState() + " " + dinfo.getException() + " delay:" + dinfo.getDelay());
          }
        }
        break;

      case DOWNLOADING:
        long now = System.currentTimeMillis();
        if(now - 1000 > last) {
          last = now;

          String parts = "";

          for(VideoFileInfo dinfo : videoFilesInfo) {
            SpeedInfo speedInfo = getSpeedInfo(dinfo);
            speedInfo.step(dinfo.getCount());

            List<Part> pp = dinfo.getParts();
            if(pp != null) {
              // multipart download
              for(Part p : pp) {
                if(p.getState().equals(States.DOWNLOADING)) {
                  parts += String.format("part#%d(%.2f) ", p.getNumber(), p.getCount() / (float)p.getLength());
                }
              }
            }
            System.out.println(String.format("file:%d - %s %.2f %s (%s)", videoFilesInfo.indexOf(dinfo), videoInfo.getState(),
                dinfo.getCount() / (float)dinfo.getLength(), parts, formatSpeed(speedInfo.getCurrentSpeed())));
          }
        }
        break;

      default:
        break;
      }
    }
  }

  private static String formatSpeed(long s)
  {
    if(s > 0.1 * 1024 * 1024 * 1024) {
      float f = s / 1024f / 1024f / 1024f;
      return String.format("%.1f GB/s", f);
    }

    if(s > 0.1 * 1024 * 1024) {
      float f = s / 1024f / 1024f;
      return String.format("%.1f MB/s", f);
    }

    float f = s / 1024f;
    return String.format("%.1f kb/s", f);
  }
}