package com.sapher.youtubedl.utils;

import com.sapher.youtubedl.callback.DownloadProgressCallback;
import com.sapher.youtubedl.callback.LineOutputCallback;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StreamProcessExtractor extends Thread {
    private InputStream stream;
    private StringBuffer buffer;
    private final LineOutputCallback callback;

    public StreamProcessExtractor(StringBuffer buffer, InputStream stream, LineOutputCallback callback) {
        this.stream = stream;
        this.buffer = buffer;
        this.callback = callback;
        this.start();
    }

    @Override
    public void run() {
        try(var input = new BufferedReader(new InputStreamReader(stream))){
            String nextLine;
            while ((nextLine = input.readLine()) != null){
                buffer.append(nextLine).append("\n");
                if (callback != null){
                    callback.handleThisLine(nextLine);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
