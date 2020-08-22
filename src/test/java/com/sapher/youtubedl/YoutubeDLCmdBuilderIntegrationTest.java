package com.sapher.youtubedl;

import org.junit.Assert;
import org.junit.Test;

public class YoutubeDLCmdBuilderIntegrationTest extends YoutubeDLCmdBuilderTestHelper {

  @Test
  public void testUsingOwnExecutablePathForOneRequest() {
    YoutubeDLRequest request = new YoutubeDLRequest();
    request.setOption("--version");
    request.setYoutubedlPath("some/other/path");
    Assert.assertNotEquals(YoutubeDL.getDefaultExecutablePath(), request.getYoutubedlPath());
    var command = YoutubeDLCmdBuilder.buildCommand(request, YoutubeDL.getDefaultExecutablePath());
    Assert.assertArrayEquals(new String[]{"some/other/path", "--version"}, command);
  }

  @Test
  public void testUsingPythonExecutableForOneRequest() {
    YoutubeDLRequest request = new YoutubeDLRequest();
    request.setOption("--version");
    request.setYoutubedlPath("python some/other/path");
    Assert.assertNotEquals(YoutubeDL.getDefaultExecutablePath(), request.getYoutubedlPath());
    String[] command = YoutubeDLCmdBuilder.buildCommand(request, YoutubeDL.getDefaultExecutablePath());
    Assert.assertArrayEquals(new String[]{"python", "some/other/path", "--version"}, command);
  }


  @Test
  public void testBuildingCommandWithDefaultExePath() {
    String[] command = YoutubeDLCmdBuilder.buildCommand(new YoutubeDLRequest(VIDEO_URL), "youtube-dl");
    Assert.assertArrayEquals(new String[]{"youtube-dl", VIDEO_URL}, command);
  }

  @Test
  public void testBuildingCommandWithCustomExePath() {
    YoutubeDLRequest request = new YoutubeDLRequest();
    request.setOption("--version");
    request.setYoutubedlPath("python some/other/path");
    String[] command = YoutubeDLCmdBuilder.buildCommand(request, "youtube-dl");
    Assert.assertArrayEquals(new String[]{"python", "some/other/path", "--version"}, command);
  }

  @Test
  public void testBuildingCommandWithCustomExePath2() {
    YoutubeDLRequest request = new YoutubeDLRequest(VIDEO_URL);
    request.setOption("--stimulate");
    request.setYoutubedlPath("dir/to/python 'some/other path'");
    String[] command = YoutubeDLCmdBuilder.buildCommand(request, "youtube-dl");
    Assert.assertArrayEquals(new String[]{"dir/to/python", "some/other path", VIDEO_URL, "--stimulate"}, command);
  }
}