# youtubedl-java [![](https://jitpack.io/v/CXwudi/youtubedl-java.svg)](https://jitpack.io/#CXwudi/youtubedl-java)

A simple java wrapper for [youtube-dl](https://github.com/rg3/youtube-dl) executable

## Modification
This youtubedl-java is forked from [main stream](https://github.com/sapher/youtubedl-java) 
with the following modification: 

1. give users an option to write their own custom stdout parsing code, by implementing the
[LineOutputCallback](src\main\java\com\sapher\youtubedl\callback\LineOutputCallback.java) interface
2. setting global default youtube-dl executable path
    - supporting both exe path or `python __main__.py` path
3. setting another youtube-dl executable path for a single youtube-dl request

## Prerequisite

🚨 Youtube-dl should be installed and available in your `$PATH`.

[How to properly install YoutubeDL executable](https://rg3.github.io/youtube-dl/download.html)

Otherwise, please set your own youtube-dl executable file path through `YoutubeDL.setDefaultExecutablePath(yourExePath)`.

`yourExePath` can be one of:

1. just `youtube-dl`
2. `full/path/to/youtube-dl.exe`
3. `python3 path/to/youtube-dl/__main__.py`
4. `'pyth/to/python3.exe' 'path to/youtube-dl/__main__.py'`

Otherwise, you will get this error :

`Cannot run program "youtube-dl" (in directory "/Users/my/beautiful/path"): error=2, No such file or directory`

Since version 1.2+, Java 11 **is required**

## Usage

### Installation

You can use jitpack.io to add the library to your project.

[how to use youtubedl-java](https://jitpack.io/#CXwudi/youtubedl-java)

#### Gradle

*Step 1 :* Add jitpack repository to your build file

```gradle
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```

*Step 2:* Add the dependency

```gradle
dependencies {
    compile 'com.github.CXwudi:youtubedl-java:1.+'
}
```

## Make request

```java
// Video url to download
String videoUrl = "https://www.youtube.com/watch?v=dQw4w9WgXcQ";

// Destination directory
String directory = System.getProperty("user.home");

// Build request
YoutubeDLRequest request = new YoutubeDLRequest(videoUrl, directory);
request.setOption("--ignore-errors");		// --ignore-errors
request.setOption("--output", "%(id)s");	// --output "%(id)s"
request.setOption("--retries", 10);		// --retries 10

// Make request and return response
YoutubeDLResponse response = YoutubeDL.execute(request);

// Response
String stdOut = response.getOut(); // Executable output
```

You may also specify a callback to get notified about the progress of the download:

```java
YoutubeDLResponse response = YoutubeDL.execute(
    request, new LineOutputCallback() {
            @Override
            public void handleThisLine(String line) {
                System.out.println(line);
            }
        }, new LineOutputCallback() {
            @Override
            public void handleThisLine(String line) {
                System.err.println(line);
            }
        });
```

We also provide a special callback interface with regax to extract out the `progress` and `etaInSecond`

```java
YoutubeDLResponse response = YoutubeDL.execute(
    request, new DownloadProgressCallback() {
            @Override
            public void onProgressUpdate(float progress, long etaInSeconds) {
                System.out.println(String.valueOf(progress) + "%");
            }
        }, new LineOutputCallback() {
            @Override
            public void handleThisLine(String line) {
                System.err.println(line);
            }
        });
```

For more usages, just scan through the implementation of 
[`YoutubeDL.java`](src/main/java/com/sapher/youtubedl/YoutubeDL.java) and 
[`YoutubeDLRequest.java`](src/main/java/com/sapher/youtubedl/YoutubeDLRequest.java) classes

## Links

* [Youtube-dl documentation](https://github.com/sapher/youtubedl-java)
