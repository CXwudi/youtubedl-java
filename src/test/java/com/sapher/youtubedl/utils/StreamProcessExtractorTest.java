package com.sapher.youtubedl.utils;

import com.sapher.youtubedl.YoutubeDL;
import com.sapher.youtubedl.YoutubeDLException;
import com.sapher.youtubedl.YoutubeDLRequest;
import com.sapher.youtubedl.YoutubeDLResponse;
import org.junit.Test;

import static org.junit.Assert.*;

public class StreamProcessExtractorTest {

  @Test
  public void testNormalFunctionality() throws YoutubeDLException {
    YoutubeDLRequest request = new YoutubeDLRequest();
    request.setOption("verbose").setOption("help");
    //YoutubeDL.setExecutablePath("D:\\11134\\Videos\\youtube-dl-niconico-enhanced.exe");

    YoutubeDLResponse response = YoutubeDL.execute(request, System.out::println, System.out::println);
    System.out.println(response.getOut());
  }

}