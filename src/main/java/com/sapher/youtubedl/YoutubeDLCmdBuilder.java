package com.sapher.youtubedl;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * Util class for building command line from {@link YoutubeDLRequest} instance
 * @author CX无敌
 * @co-author sapher
 */
public class YoutubeDLCmdBuilder {

  /**
   * build the command <br/>
   * @param request request the youtube-dl request
   * @param defaultYoutubeDlPath the default youtube-dl path to used if request doesn't contain a custom path
   * @return a complete command that works
   */
  public static String[] buildCommand(YoutubeDLRequest request, String defaultYoutubeDlPath){
    // get arguments
    String[] options = buildOptions(request);

    // get youtube-dl exe path
    String customPath = request.getYoutubedlPath();
    String executablePath = customPath != null ? customPath : defaultYoutubeDlPath;
    String[] exes = buildExe(executablePath);

    // concat and return
    String[] command = new String[options.length + exes.length];
    System.arraycopy(exes, 0, command, 0, exes.length);
    System.arraycopy(options, 0, command, exes.length, options.length);
    return command;

  }

  /**
   * build the exe path carefully
   */
  public static String[] buildExe(String youtubeDlPath) {

    String[] exes;
    // if user use single quote, transform them to double quote
    youtubeDlPath = youtubeDlPath.replace("'", "\"");

    if (youtubeDlPath.toLowerCase().contains("python") && youtubeDlPath.contains(" ")){
      /*
       * consider user might set executable path like
       * 1. python my/beautiful/path/__main__.py
       * 2. python3 "my/beautiful path/youtube_dl/__main__.py"
       * 3. wonderful/python/path/python my/beautiful/path/__main__.py
       * 4. "wonderful path/python.exe" "my/beautiful path/youtube_dl/__main__.py"
       * 5. "wonderful path/python.exe" my/beautiful/path/__main__.py
       */

      // handle situation 2,4,5
      if (youtubeDlPath.contains("\"")){
        var splittedStrings = youtubeDlPath.split("\"");
        exes = Arrays.stream(splittedStrings)
            .filter(StringUtils::isNotBlank)
            .map(String::trim)
            .toArray(String[]::new);
      }

      // handle situation 1,3
      else {
        var firstSpace = youtubeDlPath.indexOf(' ');
        var pythonExe = youtubeDlPath.substring(0, firstSpace);
        var rest = youtubeDlPath.substring(firstSpace + 1);
        exes = new String[]{pythonExe, rest};
      }

    } else { // user only set a .../youtube-dl.exe, which should be traded as an integrated string
      exes = new String[]{youtubeDlPath};
    }

    return exes;
  }

  /**
   * Transform options to a complete arguments list
   * @return the arguments list
   */
  public static String[] buildOptions(YoutubeDLRequest request){

    List<String> builder = new LinkedList<>();
    String url = request.getUrl();
    Map<String, String> options = request.getOption();

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
