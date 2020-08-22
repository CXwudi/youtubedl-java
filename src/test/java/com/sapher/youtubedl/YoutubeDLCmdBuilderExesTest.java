package com.sapher.youtubedl;

import org.junit.Assert;
import org.junit.Test;

public class YoutubeDLCmdBuilderExesTest extends YoutubeDLCmdBuilderTestHelper {


  @Test public void testBuildingNormalExeCommand(){
    var exeCommand = YoutubeDLCmdBuilder.buildExe("youtube-dl");
    Assert.assertArrayEquals(new String[]{"youtube-dl"}, exeCommand);
  }

  @Test public void testBuildingFullPathExeCommand(){
    var exeCommand = YoutubeDLCmdBuilder.buildExe("some/other/path");
    Assert.assertArrayEquals(new String[]{"some/other/path"}, exeCommand);
  }

  @Test public void testBuildingFullPathExeCommandWithSpace(){
    var exeCommand = YoutubeDLCmdBuilder.buildExe("my beautiful/path");
    Assert.assertArrayEquals(new String[]{"my beautiful/path"}, exeCommand);
  }

  @Test public void testBuildingFullPathExeCommand2(){
    var exeCommand = YoutubeDLCmdBuilder.buildExe("beautiful/exe/path/in/python/dir");
    Assert.assertArrayEquals(new String[]{"beautiful/exe/path/in/python/dir"}, exeCommand);
  }

  @Test public void testBuildingNormalPythonExeCommand(){
    var exeCommand = YoutubeDLCmdBuilder.buildExe("python3 some/other/path");
    Assert.assertArrayEquals(new String[]{"python3", "some/other/path"}, exeCommand);
  }

  @Test public void testBuildingFullPathPythonExeCommand(){
    var exeCommand = YoutubeDLCmdBuilder.buildExe("other/python/path/python3.exe some/other/path");
    Assert.assertArrayEquals(new String[]{"other/python/path/python3.exe", "some/other/path"}, exeCommand);
  }

  @Test public void testBuildingFullPathPythonExeCommandWithSpace(){
    var exeCommand = YoutubeDLCmdBuilder.buildExe("other/python/path/python3.exe 'my beautiful/path'");
    Assert.assertArrayEquals(new String[]{"other/python/path/python3.exe", "my beautiful/path"}, exeCommand);
  }

  @Test public void testBuildingFullPathPythonExeCommandWithSpace2(){
    var exeCommand = YoutubeDLCmdBuilder.buildExe("\"other/python path/python\" 'my beautiful/path'");
    Assert.assertArrayEquals(new String[]{"other/python path/python", "my beautiful/path"}, exeCommand);
  }

  @Test public void testBuildingFullPathPythonExeCommandWithSpace3(){
    var exeCommand = YoutubeDLCmdBuilder.buildExe("\"other/python path/python\" some/other/path");
    Assert.assertArrayEquals(new String[]{"other/python path/python", "some/other/path"}, exeCommand);
  }
}