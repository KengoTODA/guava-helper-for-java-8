package jp.skypencil.guava.stream;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import com.google.common.collect.Table;

public final class GuavaCollectors {
    private static final <T> BinaryOperator<T> throwingMerger() {
        return (value, another) -> {
            throw new IllegalStateException(String.format("Duplicated value %s", value));
        };
    }

    public static <T,R,C,V> Collector<T, ?, Table<R, C, V>> toTable(
            Function<? super T, ? extends R> rowMapper,
            Function<? super T, ? extends C> columnMapper,
            Function<? super T, ? extends V> valueMapper) {
        return toTable(rowMapper, columnMapper, valueMapper, throwingMerger());
    }

    public static <T,R,C,V> Collector<T, ?, Table<R, C, V>> toTable(
            Function<? super T, ? extends R> rowMapper,
            Function<? super T, ? extends C> columnMapper,
            Function<? super T, ? extends V> valueMapper,
            BinaryOperator<V> mergeFunction) {


        return new TableCollector<T, R, C, V, Table<R, C, V>>(
                rowMapper, columnMapper, valueMapper, mergeFunction) {
            @Override
            public Function<Table<R, C, V>, Table<R, C, V>> finisher() {
                return Function.identity();
            }

            @Override
            public Set<Characteristics> characteristics() {
                return CharacteristicSets.IDENTITY;
            }
        };
    }

    public static <T, K, U> Collector<T, ?, BiMap<K, U>> toBiMap(
            Function<? super T, ? extends K> keyMapper,
            Function<? super T, ? extends U> valueMapper) {
        return toBiMap(keyMapper, valueMapper, throwingMerger());
    }

    public static <T, K, U> Collector<T, ?, BiMap<K, U>> toBiMap(
            Function<? super T, ? extends K> keyMapper,
            Function<? super T, ? extends U> valueMapper,
            BinaryOperator<U> mergeFunction) {
        return new BiMapCollector<T, K, U, BiMap<K, U>>(keyMapper, valueMapper,
                mergeFunction) {
            @Override
            public Function<BiMap<K, U>, BiMap<K, U>> finisher() {
                return Function.identity();
            }

            @Override
            public Set<Characteristics> characteristics() {
                return CharacteristicSets.IDENTITY;
            }
        };
    }

    public static <T, K, U> Collector<T, ?, ImmutableBiMap<K, U>> toImmutableBiMap(
            Function<? super T, ? extends K> keyMapper,
            Function<? super T, ? extends U> valueMapper,
            BinaryOperator<U> mergeFunction) {
        return new BiMapCollector<T, K, U, ImmutableBiMap<K, U>>(keyMapper,
                valueMapper, mergeFunction) {
            @Override
            public Function<BiMap<K, U>, ImmutableBiMap<K, U>> finisher() {
                return map -> {
                    ImmutableBiMap.Builder<K, U> builder = ImmutableBiMap
                            .builder();
                    return builder.putAll(map).build();
                };
            }

            @Override
            public Set<Characteristics> characteristics() {
                return CharacteristicSets.EMPTY;
            }
        };
    }

    public static <T, K, U> Collector<T, ?, Multimap<K, U>> toMultimap(
            Function<? super T, ? extends K> keyMapper,
            Function<? super T, ? extends U> valueMapper) {
        return new MultimapCollector<T, K, U, Multimap<K, U>>(keyMapper,
                valueMapper) {
            @Override
            public Function<Multimap<K, U>, Multimap<K, U>> finisher() {
                return Function.identity();
            }

            @Override
            public Set<Characteristics> characteristics() {
                return CharacteristicSets.IDENTITY;
            }
        };
    }

    public static <T, K, U> Collector<T, ?, ImmutableMultimap<K, U>> toImmutableMultimap(
            Function<? super T, ? extends K> keyMapper,
            Function<? super T, ? extends U> valueMapper) {
        return new MultimapCollector<T, K, U, ImmutableMultimap<K, U>>(
                keyMapper, valueMapper) {
            @Override
            public Function<Multimap<K, U>, ImmutableMultimap<K, U>> finisher() {
                return map -> {
                    ImmutableMultimap.Builder<K, U> builder = ImmutableMultimap
                            .builder();
                    return builder.putAll(map).build();
                };
            }

            @Override
            public Set<Characteristics> characteristics() {
                return CharacteristicSets.EMPTY;
            }
        };
    }

    public static <T> Collector<T, ?, Multiset<T>> toMultiset() {
        return new MultisetCollector<T, Multiset<T>>() {
            @Override
            public Function<Multiset<T>, Multiset<T>> finisher() {
                return Function.identity();
            }

            @Override
            public Set<Characteristics> characteristics() {
                return CharacteristicSets.IDENTITY;
            }
        };
    }

    private GuavaCollectors() {}
    // TODO groupingBy
}
