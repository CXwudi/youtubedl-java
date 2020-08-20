package com.sapher.youtubedl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class YoutubeDLRequestTest {

    private final String youtubeDlExe = "youtube-dl";

    @Before
    public void setDefaultExe(){
        if (!YoutubeDL.getDefaultExecutablePath().equals(youtubeDlExe)) {
            YoutubeDL.setDefaultExecutablePath(youtubeDlExe);
        }
    }

    @Test
    public void testBuildOptionStandalone() {

        YoutubeDLRequest request = new YoutubeDLRequest();
        request.setOption("--help");

        Assert.assertArrayEquals(new String[]{youtubeDlExe,"--help"} , request.buildCommand(youtubeDlExe));
    }

    @Test
    public void testBuildOptionStandaloneWithEmptyValue() {

        YoutubeDLRequest request = new YoutubeDLRequest();
        request.setOption("--help", " ");

        Assert.assertArrayEquals(new String[]{youtubeDlExe,"--help"} , request.buildCommand(youtubeDlExe));
    }

    @Test
    public void testBuildOptionWithValue() {

        YoutubeDLRequest request = new YoutubeDLRequest();
        request.setOption("--password", "1234");

        Assert.assertArrayEquals(new String[]{youtubeDlExe,"--password","1234"}, request.buildCommand(youtubeDlExe));
    }

    @Test
    public void testBuildChainOptionWithValue() {

        YoutubeDLRequest request = new YoutubeDLRequest();
        request.setOption("--password", "1234");
        request.setOption("--username", "1234");

        Assert.assertArrayEquals(new String[]{youtubeDlExe,"--password","1234","--username","1234"}, request.buildCommand(youtubeDlExe));
    }

    @Test
    public void testBuildChainOptionWithMap() {

        YoutubeDLRequest request = new YoutubeDLRequest();
        Map<String, String> map = new HashMap<>();
        map.put("--password", "1234");
        map.put("--username", "1234");
        request.setOptions(map);

        Assert.assertArrayEquals(new String[]{youtubeDlExe,"--password","1234","--username","1234"}, request.buildCommand(youtubeDlExe));
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

        Assert.assertArrayEquals(new String[]{youtubeDlExe,"--password","1234","--username","1234"}, request.buildCommand(youtubeDlExe));
    }

    @Test
    public void testBuildingNormalDownload(){
        String url = "https://www.youtube.com/watch?v=AufydOsiD6M";
        YoutubeDLRequest request = new YoutubeDLRequest();
        request.setUrl(url);
        request.setOption("--simulate");

        Assert.assertArrayEquals(new String[]{youtubeDlExe, url, "--simulate"}, request.buildCommand(youtubeDlExe));

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
        Assert.assertEquals(3, request.buildCommand(youtubeDlExe).length);
    }

    @Test
    public void testConstructingRequestVerbose(){
        YoutubeDLRequest request = new YoutubeDLRequest("", " ", " ");
        Assert.assertNull(request.getDirectory());
        Assert.assertNull(request.getYoutubedlPath());
    }
}
