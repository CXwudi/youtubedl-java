package com.sapher.youtubedl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sapher.youtubedl.callback.LineOutputCallback;
import com.sapher.youtubedl.mapper.VideoFormat;
import com.sapher.youtubedl.mapper.VideoInfo;
import com.sapher.youtubedl.mapper.VideoThumbnail;
import com.sapher.youtubedl.utils.StreamProcessExtractor;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * <p>Provide an interface for youtube-dl executable</p>
 * <p>
 *     All methods are declared as static since they are thread-safe,
 *     as long as the {@link YoutubeDL#defaultExecutablePath} and the {@link YoutubeDLRequest} instance
 *     are not modified during the execution of any methods,
 * </p>
 * <p>
 *     For more information on youtube-dl, please see
 *     <a href="https://github.com/rg3/youtube-dl/blob/master/README.md">YoutubeDL Documentation</a>
 * </p>
 */
public class YoutubeDL {

    /**
     * By default this is Youtube-dl executable name.
     * Assuming youtube-dl executable is in the PATH
     */
    protected static String defaultExecutablePath = "youtube-dl";

    /**
     * Execute youtube-dl request
     * @param request request object
     * @return response object
     * @throws YoutubeDLException
     */
    public static YoutubeDLResponse execute(YoutubeDLRequest request) throws YoutubeDLException {
        return execute(request, null, null);
    }

    /**
     * Execute youtube-dl request
     * @param request request object
     * @param stdOutCallback stdOutCallback
     * @return response object
     * @throws YoutubeDLException
     */
    public static YoutubeDLResponse execute(YoutubeDLRequest request,
                                            LineOutputCallback stdOutCallback,
                                            LineOutputCallback stdErrCallback) throws YoutubeDLException {

        String[] command = YoutubeDLCmdBuilder.buildCommand(request, defaultExecutablePath);
        String directory = request.getDirectory();

        Process process;
        int exitCode;
        StringBuilder outBuffer = new StringBuilder(); //stdout
        StringBuilder errBuffer = new StringBuilder(); //stderr
        long startTime = System.nanoTime();

        ProcessBuilder processBuilder = new ProcessBuilder(command);

        // Define directory if one is passed
        if(directory != null) {
            processBuilder.directory(new File(directory));
        }

        try {
            process = processBuilder.start();
        } catch (IOException e) {
            throw new YoutubeDLException(e);
        }

        InputStream outStream = process.getInputStream();
        InputStream errStream = process.getErrorStream();

        StreamProcessExtractor stdOutProcessor = new StreamProcessExtractor(outBuffer, outStream, stdOutCallback);
        StreamProcessExtractor stdErrProcessor = new StreamProcessExtractor(errBuffer, errStream, stdErrCallback);

        try {
            stdOutProcessor.join();
            stdErrProcessor.join();
            exitCode = process.waitFor();
        } catch (InterruptedException e) {
            // process exited for some reason
            throw new YoutubeDLException(e);
        }

        String out = outBuffer.toString();
        String err = errBuffer.toString();

        // we want response even though youtube-dl fails, so we can do our workaround
//        if(exitCode > 0) {
//            throw new YoutubeDLException(err);
//        }

        int elapsedTime = (int) ((System.nanoTime() - startTime) / 1000000);

        return new YoutubeDLResponse(command, request.getOption(), directory, exitCode , elapsedTime, out, err);

    }


    /**
     * Get youtube-dl executable version
     * @return version string
     * @throws YoutubeDLException
     */
    public static String getVersion() throws YoutubeDLException {
        YoutubeDLRequest request = new YoutubeDLRequest();
        request.setOption("--version");
        return YoutubeDL.execute(request).getOut();
    }

    /**
     * Retrieve all information available on a video
     * @param url Video url
     * @return Video info
     * @throws YoutubeDLException
     */
    public static VideoInfo getVideoInfo(String url) throws YoutubeDLException  {

        // Build request
        YoutubeDLRequest request = new YoutubeDLRequest(url);
        request.setOption("--dump-json");
        request.setOption("--no-playlist");
        YoutubeDLResponse response = YoutubeDL.execute(request);

        // Parse result
        ObjectMapper objectMapper = new ObjectMapper();
        VideoInfo videoInfo;

        try {
            videoInfo = objectMapper.readValue(response.getOut(), VideoInfo.class);
        } catch (IOException e) {
            throw new YoutubeDLException("Unable to parse video information: " + e.getMessage(), e);
        }

        return videoInfo;
    }

    /**
     * List formats
     * @param url Video url
     * @return list of formats
     * @throws YoutubeDLException
     */
    public static List<VideoFormat> getFormats(String url) throws YoutubeDLException {
        VideoInfo info = getVideoInfo(url);
        return info.formats;
    }

    /**
     * List thumbnails
     * @param url Video url
     * @return list of thumbnail
     * @throws YoutubeDLException
     */
    public static List<VideoThumbnail> getThumbnails(String url) throws YoutubeDLException {
        VideoInfo info = getVideoInfo(url);
        return info.thumbnails;
    }

    /**
     * List categories
     * @param url Video url
     * @return list of category
     * @throws YoutubeDLException
     */
    public static List<String> getCategories(String url) throws YoutubeDLException {
        VideoInfo info = getVideoInfo(url);
        return info.categories;
    }

    /**
     * List tags
     * @param url Video url
     * @return list of tag
     * @throws YoutubeDLException
     */
    public static List<String> getTags(String url) throws YoutubeDLException {
        VideoInfo info = getVideoInfo(url);
        return info.tags;
    }

    /**
     * Get command executable or path to the executable
     * @return path string, can be "pyhton my/youtube-dl/main.py" as well
     */
    public static String getDefaultExecutablePath(){
        return defaultExecutablePath;
    }

    /**
     * Set path to use for the command, can be "pyhton my/youtube-dl/main.py" as well
     * @param path String path to the executable
     */
    public static void setDefaultExecutablePath(String path){
        defaultExecutablePath = path;
    }
}
