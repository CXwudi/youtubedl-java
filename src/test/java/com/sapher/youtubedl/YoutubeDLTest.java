package com.sapher.youtubedl;

import com.sapher.youtubedl.mapper.VideoFormat;
import com.sapher.youtubedl.mapper.VideoInfo;
import com.sapher.youtubedl.mapper.VideoThumbnail;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.Assert;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class YoutubeDLTest {

    private final static String DIRECTORY = System.getProperty("java.io.tmpdir");
    private final static String VIDEO_URL = "https://www.youtube.com/watch?v=dQw4w9WgXcQ";
    private final static String NONE_EXISTENT_VIDEO_URL = "https://www.youtube.com/watch?v=dQw4w9WgXcZ";

    /**
     * modify this to make test case running
     */
    @Before
    public void setDefaultExe() {
        //YoutubeDL.setDefaultExecutablePath("D:\\11134\\Videos\\youtube-dl-niconico-enhanced.exe");
        YoutubeDL.setDefaultExecutablePath("python D:\\11134\\Videos\\Vocaloid Coding POC\\youtube-dl-niconico-enhanced\\youtube_dl/__main__.py");
    }

    @Test
    public void testGetVersion() throws YoutubeDLException {
        Assert.assertNotNull(YoutubeDL.getVersion());
    }

    @Test
    public void testElapsedTime() throws YoutubeDLException {

        long startTime = System.nanoTime();

        YoutubeDLRequest request = new YoutubeDLRequest();
        request.setOption("--version");
        YoutubeDLResponse response = YoutubeDL.execute(request);

        int elapsedTime = (int) ((System.nanoTime() - startTime) / 1000000);
        System.out.println("wrapped elapsedTime = " + elapsedTime + ", real elapsedTime = " + response.getElapsedTime());
        Assert.assertTrue(elapsedTime >= response.getElapsedTime());
    }


    @Test
    public void testSimulateDownload() throws YoutubeDLException {

        YoutubeDLRequest request = new YoutubeDLRequest();
        request.setUrl(VIDEO_URL);
        request.setOption("--simulate");

        YoutubeDLResponse response = YoutubeDL.execute(request);
        Assert.assertEquals("", response.getErr());
    }

    @Test
    public void testDirectory() throws YoutubeDLException {

        YoutubeDLRequest request = new YoutubeDLRequest(VIDEO_URL, DIRECTORY);
        request.setOption("--simulate");

        YoutubeDLResponse response = YoutubeDL.execute(request);

        Assert.assertEquals(DIRECTORY, response.getDirectory());
    }

    @Test
    public void testGetVideoInfo() throws YoutubeDLException {
        VideoInfo videoInfo = YoutubeDL.getVideoInfo(VIDEO_URL);
        Assert.assertNotNull(videoInfo);
    }

    @Test
    public void testGetFormats() throws YoutubeDLException {
        List<VideoFormat> formats = YoutubeDL.getFormats(VIDEO_URL);
        Assert.assertNotNull(formats);
        Assert.assertTrue(formats.size() > 0);
    }

    @Test
    public void testGetThumbnails() throws YoutubeDLException {
        List<VideoThumbnail> thumbnails = YoutubeDL.getThumbnails(VIDEO_URL);
        Assert.assertNotNull(thumbnails);
        Assert.assertTrue(thumbnails.size() > 0);
    }

    @Test
    public void testGetTags() throws YoutubeDLException {
        List<String> tags = YoutubeDL.getTags(VIDEO_URL);
        Assert.assertNotNull(tags);
        Assert.assertTrue(tags.size() > 0);
    }

    @Test
    public void testGetCategories() throws YoutubeDLException {
        List<String> categories = YoutubeDL.getCategories(VIDEO_URL);
        Assert.assertNotNull(categories);
        Assert.assertTrue(categories.size() > 0);
    }

    @Test(expected = YoutubeDLException.class)
    public void testFailGetNonExistentVideoInfo() throws YoutubeDLException {
        YoutubeDL.getVideoInfo(NONE_EXISTENT_VIDEO_URL);
    }
}