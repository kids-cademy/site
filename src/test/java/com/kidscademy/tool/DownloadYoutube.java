package com.kidscademy.tool;

import java.io.File;
import java.net.URL;

import com.github.axet.vget.VGet;

public class DownloadYoutube
{
  public static void main(String[] args) {
    try {
        String url = "https://www.youtube.com/watch?v=UJNjlQEy_CA";
        //String url = "https://www.youtube.com/watch?v=aWNPIecARCc";
          
        //String url = "https://www.youtube.com/watch?v=s10ARdfQUOY";
        String path = "D:\\tmp\\youtube\\";
        VGet v = new VGet(new URL(url), new File(path));
        v.download();
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
}
}
