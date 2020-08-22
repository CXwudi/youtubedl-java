package com.sapher.youtubedl;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    YoutubeDLCmdBuilderOptionsTest.class,
    YoutubeDLCmdBuilderExesTest.class,
    YoutubeDLCmdBuilderIntegrationTest.class
})
public class YoutubeDLCmdBuilderTestSuit {
}
