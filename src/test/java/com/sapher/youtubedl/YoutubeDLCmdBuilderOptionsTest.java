package com.sapher.youtubedl;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class YoutubeDLCmdBuilderOptionsTest extends YoutubeDLCmdBuilderTestHelper {

    @Test
    public void testBuildOptionStandalone() {

        YoutubeDLRequest request = new YoutubeDLRequest();
        request.setOption("--help");

        Assert.assertArrayEquals(new String[]{"--help"} , YoutubeDLCmdBuilder.buildOptions(request));
    }

    @Test
    public void testBuildOptionStandaloneWithEmptyValue() {

        YoutubeDLRequest request = new YoutubeDLRequest();
        request.setOption("--help", " ");

        Assert.assertArrayEquals(new String[]{"--help"} , YoutubeDLCmdBuilder.buildOptions(request));
    }

    @Test
    public void testBuildOptionWithValue() {

        YoutubeDLRequest request = new YoutubeDLRequest();
        request.setOption("--password", "1234");

        Assert.assertArrayEquals(new String[]{"--password","1234"}, YoutubeDLCmdBuilder.buildOptions(request));
    }

    @Test
    public void testBuildChainOptionWithValue() {

        YoutubeDLRequest request = new YoutubeDLRequest();
        request.setOption("--password", "1234");
        request.setOption("--username", 1234);

        Assert.assertArrayEquals(new String[]{"--password","1234","--username","1234"}, YoutubeDLCmdBuilder.buildOptions(request));
    }

    @Test
    public void testBuildChainOptionWithMap() {

        YoutubeDLRequest request = new YoutubeDLRequest();
        Map<String, String> map = new HashMap<>();
        map.put("--password", "1234");
        map.put("--username", "1234");
        request.setOptions(map);

        Assert.assertArrayEquals(new String[]{"--password","1234","--username","1234"}, YoutubeDLCmdBuilder.buildOptions(request));
    }

    @Test
    public void testBuildChainOptionWithMapVerbose() {

        YoutubeDLRequest request = new YoutubeDLRequest();
        Map<String, String> map = new HashMap<>();
        map.put("--password", "1234");
        map.put("--username", "1234");
        map.put(" ", "");
        map.put(null, " ");

        request.setOptions(map);
        request.setOption(" ").setOption("").setOption(null);

        Assert.assertArrayEquals(new String[]{"--password","1234","--username","1234"}, YoutubeDLCmdBuilder.buildOptions(request));
    }

    @Test
    public void testBuildingNormalDownloadOption(){
        YoutubeDLRequest request = new YoutubeDLRequest();
        request.setUrl(VIDEO_URL);
        request.setOption("--simulate");

        Assert.assertArrayEquals(new String[]{VIDEO_URL, "--simulate"}, YoutubeDLCmdBuilder.buildOptions(request));
    }

    @Test
    public void testConstructingRequestVerbose(){
        YoutubeDLRequest request = new YoutubeDLRequest("", " ", " ");
        Assert.assertNull(request.getDirectory());
        Assert.assertNull(request.getYoutubedlPath());
    }

}
