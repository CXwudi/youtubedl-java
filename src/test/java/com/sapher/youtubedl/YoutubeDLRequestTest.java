package com.sapher.youtubedl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class YoutubeDLRequestTest {

    @Before
    public void setDefaultExe(){
        if (!YoutubeDL.getDefaultExecutablePath().equals("youtube-dl")) {
            YoutubeDL.setDefaultExecutablePath("youtube-dl");
        }
    }

    @Test
    public void testBuildOptionStandalone() {

        YoutubeDLRequest request = new YoutubeDLRequest();
        request.setOption("--help");

        Assert.assertArrayEquals(new String[]{"youtube-dl","--help"} , request.buildCommand());
    }

    @Test
    public void testBuildOptionStandaloneWithEmptyValue() {

        YoutubeDLRequest request = new YoutubeDLRequest();
        request.setOption("--help", " ");

        Assert.assertArrayEquals(new String[]{"youtube-dl","--help"} , request.buildCommand());
    }

    @Test
    public void testBuildOptionWithValue() {

        YoutubeDLRequest request = new YoutubeDLRequest();
        request.setOption("--password", "1234");

        Assert.assertArrayEquals(new String[]{"youtube-dl","--password","1234"}, request.buildCommand());
    }

    @Test
    public void testBuildChainOptionWithValue() {

        YoutubeDLRequest request = new YoutubeDLRequest();
        request.setOption("--password", "1234");
        request.setOption("--username", "1234");

        Assert.assertArrayEquals(new String[]{"youtube-dl","--password","1234","--username","1234"}, request.buildCommand());
    }

    @Test
    public void testBuildChainOptionWithMap() {

        YoutubeDLRequest request = new YoutubeDLRequest();
        Map<String, String> map = new HashMap<>();
        map.put("--password", "1234");
        map.put("--username", "1234");
        request.setOptions(map);

        Assert.assertArrayEquals(new String[]{"youtube-dl","--password","1234","--username","1234"}, request.buildCommand());
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

        Assert.assertArrayEquals(new String[]{"youtube-dl","--password","1234","--username","1234"}, request.buildCommand());
    }


    @Test
    public void testUsingOwnExecutablePathForOneRequest() {
        YoutubeDLRequest request = new YoutubeDLRequest();
        request.setOption("--version");
        request.setYoutubedlPath("some/other/path");
        Assert.assertNotEquals(YoutubeDL.getDefaultExecutablePath(), request.getYoutubedlPath());
    }

    @Test
    public void testUsingPythonExecutableForOneRequest() {
        YoutubeDLRequest request = new YoutubeDLRequest();
        request.setOption("--version");
        request.setYoutubedlPath("python some/other/path");
        Assert.assertNotEquals(YoutubeDL.getDefaultExecutablePath(), request.getYoutubedlPath());
        Assert.assertEquals(3, request.buildCommand().length);
    }
}
