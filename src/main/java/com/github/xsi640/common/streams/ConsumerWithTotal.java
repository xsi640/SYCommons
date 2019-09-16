package com.github.xsi640.common.streams;

import java.util.function.Consumer;

public interface ConsumerWithTotal<T> extends Consumer<T> {
    void accept(T t, long total);
}
