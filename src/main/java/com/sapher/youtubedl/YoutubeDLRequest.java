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
        setOption(key, null);
        return this;
    }

    /**
     * set the option, if value is empty string after striped,
     * then {@link YoutubeDLRequest#setOption(String)} is used
     * @return {@code this}
     */
    public YoutubeDLRequest setOption(String key, String value) {
        if (value == null || "".equals(value.strip())){
            options.put(key, null);
        } else {
            options.put(key, value);
        }
        return this;
    }

    public YoutubeDLRequest setOption(String key, int value) {
        options.put(key, String.valueOf(value));
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
            String key = entry.getKey();
            if (key != null && !"".equals(key.strip())) {
                setOption(key, entry.getValue());
            }
        }
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
    public String[] buildCommand() {

        List<String> builder = new LinkedList<>();

        // Set youtube-dl exe
        String executablePath;
        if (youtubedlPath != null) {
            executablePath = youtubedlPath;
        } else {

            executablePath = YoutubeDL.getDefaultExecutablePath();
        }

        // consider user might set executable path like "python.exe youtube_dl/__main__.py"
        builder.addAll(Arrays.asList(executablePath.split(" ")));

        // Set Url
        if(url != null) {
            builder.add(url);
        }

        // Build options strings
        Iterator<Map.Entry<String, String>> it = options.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> option = it.next();

            String name = option.getKey();
            String value = option.getValue();

            builder.add(name);
            if(value != null && !"".equals(value)) {
                builder.add(value);
            }

            it.remove();
        }

        return builder.toArray(new String[0]);
    }
}
