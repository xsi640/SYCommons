package com.github.xsi640.common.strings;

public class MissingKeyException extends RuntimeException {

    public MissingKeyException(String key) {
        super("You didn't pass an arg for key " + key);
    }
}
