package jp.skypencil.guava.stream;

import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;
import java.util.stream.Collector;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

abstract class MultisetCollector<T, R extends Multiset<T>>
        implements Collector<T, Multiset<T>, R> {

    @Override
    public Supplier<Multiset<T>> supplier() {
        return HashMultiset::create;
    }

    @Override
    public BiConsumer<Multiset<T>, T> accumulator() {
        return Multiset::add;
    }

    @Override
    public BinaryOperator<Multiset<T>> combiner() {
        return (set, another) -> {
            set.addAll(another);
            return set;
        };
    }
}