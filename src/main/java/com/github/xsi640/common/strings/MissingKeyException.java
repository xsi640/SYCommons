package com.github.xsi640.common.strings;

/**
 * 参数没有找到的异常
 */
public class MissingKeyException extends RuntimeException {

    public MissingKeyException(String key) {
        super("You didn't pass an arg for key " + key);
    }
}
