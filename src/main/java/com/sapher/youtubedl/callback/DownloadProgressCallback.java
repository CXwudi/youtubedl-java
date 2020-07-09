package com.sapher.youtubedl.callback;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A special {@link LineOutputCallback} that use regax to parse youtube-dl progress,
 * and nicely output it base on user code
 * @author sapher
 * @co-author CX无敌
 */
@FunctionalInterface
public interface DownloadProgressCallback extends LineOutputCallback{
    static final String GROUP_PERCENT = "percent";
    static final String GROUP_MINUTES = "minutes";
    static final String GROUP_SECONDS = "seconds";
    static final Pattern p = Pattern.compile("\\[download\\]\\s+(?<percent>\\d+\\.\\d)% .* ETA (?<minutes>\\d+):(?<seconds>\\d+)");

    void onProgressUpdate(float progress, long etaInSeconds);

    @Override
    default void handleThisLine(String line){
        Matcher m = p.matcher(line);
        if (m.matches()) {
            float progress = Float.parseFloat(m.group(GROUP_PERCENT));
            long eta = convertToSeconds(m.group(GROUP_MINUTES), m.group(GROUP_SECONDS));
            onProgressUpdate(progress, eta);
        }
    }

    private int convertToSeconds(String minutes, String seconds) {
        return Integer.parseInt(minutes) * 60 + Integer.parseInt(seconds);
    }
}
