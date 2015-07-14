package jp.skypencil.guava.stream;

import java.util.EnumSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.google.common.collect.ImmutableSet;

public class GuavaCollectors {
    public static <T> Collector<T, ?, ImmutableList<T>> toImmutableList() {
        return new Collector<T, ImmutableList.Builder<T>, ImmutableList<T>>(){

            @Override
            public Supplier<Builder<T>> supplier() {
                return ImmutableList::builder;
            }

            @Override
            public BiConsumer<Builder<T>, T> accumulator() {
                return (builder, value) -> builder.add(value);
            }

            @Override
            public BinaryOperator<Builder<T>> combiner() {
                return (builder, another) -> builder.addAll(another.build());
            }

            @Override
            public Function<Builder<T>, ImmutableList<T>> finisher() {
                return ImmutableList.Builder::build;
            }

            @Override
            public Set<java.util.stream.Collector.Characteristics> characteristics() {
                return EnumSet.noneOf(java.util.stream.Collector.Characteristics.class);
            }
        };
    }

    public static <T> Collector<T, ?, ImmutableSet<T>> toImmutableSet() {
        return new Collector<T, ImmutableSet.Builder<T>, ImmutableSet<T>>(){
            @Override
            public Supplier<com.google.common.collect.ImmutableSet.Builder<T>> supplier() {
                return ImmutableSet::builder;
            }

            @Override
            public BiConsumer<com.google.common.collect.ImmutableSet.Builder<T>, T> accumulator() {
                return (builder, value) -> builder.add(value);
            }

            @Override
            public BinaryOperator<com.google.common.collect.ImmutableSet.Builder<T>> combiner() {
                return (builder, another) -> builder.addAll(another.build());
            }

            @Override
            public Function<com.google.common.collect.ImmutableSet.Builder<T>, ImmutableSet<T>> finisher() {
                return ImmutableSet.Builder::build;
            }

            @Override
            public Set<java.util.stream.Collector.Characteristics> characteristics() {
                return EnumSet.noneOf(java.util.stream.Collector.Characteristics.class);
            }
        };
    }
}
