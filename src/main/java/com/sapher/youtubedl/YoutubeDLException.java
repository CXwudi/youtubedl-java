package com.sapher.youtubedl;

/**
 * YoutubeDL Exception
 */
public class YoutubeDLException extends Exception {

    /**
     * Construct YoutubeDLException with a message
     * @param message
     */
    public YoutubeDLException(String message) {
        super(message);
    }

    /**
     * Construct YoutubeDLException from another exception
     * @param e Any exception
     */
    public YoutubeDLException(Throwable e) {
        super(e);
    }

    public YoutubeDLException(String message, Throwable e){
        super(message, e);
    }
}
