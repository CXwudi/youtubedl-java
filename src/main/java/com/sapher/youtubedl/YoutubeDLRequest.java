package com.sapher.youtubedl;

import java.util.*;

/**
 * YoutubeDL request
 */
public class YoutubeDLRequest {

    /**
     * Executable working directory
     */
    private String directory;

    /**
     * Video Url
     */
    private String url;

    /**
     * The customized youtube-dl executable path for this request only, default is {@code null}.
     * When calling {@link YoutubeDLRequest#buildCommand()},
     * {@code null} will be replaced by {@link YoutubeDL#getDefaultExecutablePath()}.
     * Otherwise, it stay the same
     */
    private String youtubedlPath;

    /**
     * List of executable options
     */
    private Map<String, String> options = new HashMap<>();

    public String getDirectory() {
        return directory;
    }

    public YoutubeDLRequest setDirectory(String directory) {
        this.directory = directory;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public YoutubeDLRequest setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getYoutubedlPath() {
        return youtubedlPath;
    }

    public YoutubeDLRequest setYoutubedlPath(String youtubedlPath) {
        this.youtubedlPath = youtubedlPath;
        return this;
    }

    public Map<String, String> getOption() {
        return options;
    }

    public YoutubeDLRequest setOption(String key) {
        options.put(key, null);
        return this;
    }

    public YoutubeDLRequest setOption(String key, String value) {
        options.put(key, value);
        return this;
    }

    public YoutubeDLRequest setOption(String key, int value) {
        options.put(key, String.valueOf(value));
        return this;
    }

    public YoutubeDLRequest setOptions(Map<String, String> options) {
        this.options.putAll(options);
        return this;
    }

    /**
     * Constructor
     */
    public YoutubeDLRequest() {
        this(null);
    }

    /**
     * Construct a request with a videoUrl
     * @param url
     */
    public YoutubeDLRequest(String url) {
        this(url, null);
    }

    /**
     * Construct a request with a videoUrl and working directory
     * @param url
     * @param directory
     */
    public YoutubeDLRequest(String url, String directory) {
        this(url, directory, null);
    }

    public YoutubeDLRequest(String url, String directory, String youtubedlPath) {
        this.url = url;
        this.directory = directory;
        this.youtubedlPath = youtubedlPath;
    }

    /**
     * Transform options to a string and build a completed working command line
     * @return Command string
     */
    public String buildCommand() {

        StringBuilder builder = new StringBuilder();

        // Set youtube-dl exe
        if (youtubedlPath != null)
            builder.append(youtubedlPath + " ");
        else
            builder.append(YoutubeDL.getDefaultExecutablePath() + " ");


        // Set Url
        if(url != null)
            builder.append(url + " ");

        // Build options strings
        Iterator<Map.Entry<String, String>> it = options.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> option = it.next();

            String name = option.getKey();
            String value = option.getValue();

            if(value == null) value = "";

            String optionFormatted = String.format("--%s %s", name, value).trim();
            builder.append(optionFormatted + " ");

            it.remove();
        }

        return builder.toString().trim();
    }
}
