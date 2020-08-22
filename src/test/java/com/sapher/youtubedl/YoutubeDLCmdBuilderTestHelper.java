package com.sapher.youtubedl;

import org.junit.Before;

public abstract class YoutubeDLCmdBuilderTestHelper {

  protected final String youtubeDlExe = "youtube-dl";
  protected final String VIDEO_URL = "https://www.youtube.com/watch?v=AufydOsiD6M";

  @Before
  public void setDefaultExe(){
    if (!YoutubeDL.getDefaultExecutablePath().equals(youtubeDlExe)) {
      YoutubeDL.setDefaultExecutablePath(youtubeDlExe);
    }
  }
}
