package com.github.xsi640.common.strings;

/**
 * Key没有找到的异常
 */
public class KeyNotFoundException extends RuntimeException {

    public KeyNotFoundException(String key, String formattingString) {
        super("Couldn't find key \"" + key + "\" in string \"" + formattingString + "\".");
    }
}