package com.github.suyang.common.strings;

public class KeyNotFoundException extends RuntimeException {

    public KeyNotFoundException(String key, String formattingString) {
        super("Couldn't find key \"" + key + "\" in string \"" + formattingString + "\".");
    }
}