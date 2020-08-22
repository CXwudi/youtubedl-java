package com.sapher.youtubedl;

import org.apache.commons.lang3.StringUtils;

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
     * When calling {@link YoutubeDLCmdBuilder#buildCommand(YoutubeDLRequest, String)},
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
        this.directory = getOrDefault(directory, null);
        return this;
    }

    public String getUrl() {
        return url;
    }

    public YoutubeDLRequest setUrl(String url) {
        this.url = getOrDefault(url, null);
        return this;
    }

    public String getYoutubedlPath() {
        return youtubedlPath;
    }

    public YoutubeDLRequest setYoutubedlPath(String youtubedlPath) {
        this.youtubedlPath = getOrDefault(youtubedlPath, null);
        return this;
    }

    public Map<String, String> getOption() {
        return options;
    }

    public YoutubeDLRequest setOption(String key) {
        setOption(key, null);
        return this;
    }

    /**
     * set the option
     * @return {@code this}
     */
    public YoutubeDLRequest setOption(String key, String value) {
        if (StringUtils.isNotBlank(key)){
            options.put(key, StringUtils.isNotBlank(value) ? value : null);
        }
        return this;
    }

    public YoutubeDLRequest setOption(String key, int value) {
        setOption(key, String.valueOf(value));
        return this;
    }

    /**
     * set options from a map of options <br/>
     * if the map contains {@code ""} or {@code null} in keys,
     * then such key-value pair is ignored
     * @param options users' options map
     * @return {@code this}
     */
    public YoutubeDLRequest setOptions(Map<String, String> options) {
        for (var entry : options.entrySet()){
            setOption(entry.getKey(), entry.getValue());
        }
        return this;
    }

    private String getOrDefault(String setValue, String defaultValue){
        return StringUtils.isBlank(setValue) ? defaultValue : setValue;
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
        setUrl(url);
        setDirectory(directory);
        setYoutubedlPath(youtubedlPath);
    }
}
