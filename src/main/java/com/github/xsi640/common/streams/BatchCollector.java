package com.github.xsi640.common.streams;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.*;
import java.util.stream.Collector;

import static java.util.Objects.requireNonNull;

class BatchCollector<T> implements Collector<T, List<T>, List<T>> {

    private final int batchSize;
    private final Consumer<List<T>> batchProcessor;
    private int start = 0;

    BatchCollector(int batchSize, ConsumerWithTotal<List<T>> batchProcessor) {
        batchProcessor = requireNonNull(batchProcessor);

        this.batchSize = batchSize;
        this.batchProcessor = batchProcessor;
    }

    BatchCollector(int batchSize, Consumer<List<T>> batchProcessor) {
        batchProcessor = requireNonNull(batchProcessor);

        this.batchSize = batchSize;
        this.batchProcessor = batchProcessor;
    }

    public Supplier<List<T>> supplier() {
        return ArrayList::new;
    }

    public BiConsumer<List<T>, T> accumulator() {
        return (ts, t) -> {
            ts.add(t);
            if (ts.size() >= batchSize) {
                if (batchProcessor instanceof ConsumerWithTotal) {
                    ((ConsumerWithTotal) batchProcessor).accept(ts, start);
                } else if (batchProcessor instanceof Consumer) {
                    batchProcessor.accept(ts);
                }
                start += ts.size();
                ts.clear();
            }
        };
    }

    public BinaryOperator<List<T>> combiner() {
        return (ts, ots) -> {
            if (batchProcessor instanceof ConsumerWithTotal) {
                ConsumerWithTotal processor = (ConsumerWithTotal) batchProcessor;
                processor.accept(ts, start);
                processor.accept(ots, start);
            } else if (batchProcessor instanceof Consumer) {
                batchProcessor.accept(ts);
                batchProcessor.accept(ots);
            }
            return Collections.emptyList();
        };
    }

    public Function<List<T>, List<T>> finisher() {
        return ts -> {
            if (batchProcessor instanceof ConsumerWithTotal) {
                ((ConsumerWithTotal) batchProcessor).accept(ts, start);
            } else if (batchProcessor instanceof Consumer) {
                batchProcessor.accept(ts);
            }
            return Collections.emptyList();
        };
    }

    public Set<Characteristics> characteristics() {
        return Collections.emptySet();
    }
}
