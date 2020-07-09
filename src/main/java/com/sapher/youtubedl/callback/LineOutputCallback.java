package com.sapher.youtubedl.callback;

/**
 * indicating how to handle a line of output from youtube-dl
 * @author CX无敌
 */
@FunctionalInterface
public interface LineOutputCallback {
  void handleThisLine(String line);
}
