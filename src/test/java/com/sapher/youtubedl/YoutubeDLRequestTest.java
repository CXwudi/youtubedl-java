package com.sapher.youtubedl;

import org.junit.Assert;
import org.junit.Test;

public class YoutubeDLRequestTest {

    @Test
    public void testBuildOptionStandalone() {

        YoutubeDLRequest request = new YoutubeDLRequest();
        request.setOption("help");

        Assert.assertEquals("youtube-dl --help", request.buildCommand());
    }

    @Test
    public void testBuildOptionWithValue() {

        YoutubeDLRequest request = new YoutubeDLRequest();
        request.setOption("password", "1234");

        Assert.assertEquals("youtube-dl --password 1234", request.buildCommand());
    }

    @Test
    public void testBuildChainOptionWithValue() {

        YoutubeDLRequest request = new YoutubeDLRequest();
        request.setOption("password", "1234");
        request.setOption("username", "1234");

        Assert.assertEquals("youtube-dl --password 1234 --username 1234", request.buildCommand());
    }
}
