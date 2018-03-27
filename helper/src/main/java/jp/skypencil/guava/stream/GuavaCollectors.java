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
    };

    /**
     * @deprecated Use {@link ImmutableList#toImmutableList()} instead.
     */
    @Deprecated
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
            public Set<Characteristics> characteristics() {
                return CharacteristicSets.EMPTY;
            }
        };
    }

    /**
     * @deprecated Use {@link ImmutableSet#toImmutableSet()} instead.
     */
    @Deprecated
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
            public Set<Characteristics> characteristics() {
                return CharacteristicSets.EMPTY;
            }
        };
    }

    /**
     * @deprecated Use {@link ImmutableMap#toImmutableMap(Function, Function)} instead.
     */
    @Deprecated
    public static <T, K, U> Collector<T, ?, ImmutableMap<K, U>> toImmutableMap(
            Function<? super T, ? extends K> keyMapper,
            Function<? super T, ? extends U> valueMapper) {
        return toImmutableMap(keyMapper, valueMapper, throwingMerger());
    }

    /**
     * @deprecated Use {@link ImmutableMap#toImmutableMap(Function, Function, BinaryOperator)} instead.
     */
    @Deprecated
    public static <T, K, U> Collector<T, ?, ImmutableMap<K, U>> toImmutableMap(
            Function<? super T, ? extends K> keyMapper,
            Function<? super T, ? extends U> valueMapper,
            BinaryOperator<U> mergeFunction) {

        return new Collector<T, Map<K, U>, ImmutableMap<K, U>>(){
            @Override
            public Supplier<Map<K, U>> supplier() {
                return HashMap::new;
            }

            @Override
            public BiConsumer<Map<K, U>, T> accumulator() {
                return (map, entry) -> {
                    K key = keyMapper.apply(entry);
                    U value = valueMapper.apply(entry);
                    map.merge(key, value, mergeFunction);
                };
            }

            @Override
            public BinaryOperator<Map<K, U>> combiner() {
                return (map, another) -> {
                    another.forEach((key, value) -> {
                        map.merge(key, value, mergeFunction);
                    });
                    return map;
                };
            }

            @Override
            public Function<Map<K, U>, ImmutableMap<K, U>> finisher() {
                return (map) -> {
                    ImmutableMap.Builder<K, U> builder = ImmutableMap.builder();
                    return builder.putAll(map).build();
                };
            }

            @Override
            public Set<Characteristics> characteristics() {
                return CharacteristicSets.EMPTY;
            }
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

    /**
     * @deprecated Use {@link ImmutableTable#toImmutableTable(Function, Function, Function)} instead.
     */
    @Deprecated
    public static <T,R,C,V> Collector<T, ?, ImmutableTable<R, C, V>> toImmutableTable(
            Function<? super T, ? extends R> rowMapper,
            Function<? super T, ? extends C> columnMapper,
            Function<? super T, ? extends V> valueMapper) {
        return toImmutableTable(rowMapper, columnMapper, valueMapper, throwingMerger());
    }

    /**
     * @deprecated Use {@link ImmutableTable#toImmutableTable(Function, Function, Function, BinaryOperator)} instead.
     */
    @Deprecated
    public static <T,R,C,V> Collector<T, ?, ImmutableTable<R, C, V>> toImmutableTable(
            Function<? super T, ? extends R> rowMapper,
            Function<? super T, ? extends C> columnMapper,
            Function<? super T, ? extends V> valueMapper,
            BinaryOperator<V> mergeFunction) {

        return new TableCollector<T, R, C, V, ImmutableTable<R, C, V>>(
                rowMapper, columnMapper, valueMapper, mergeFunction) {
            @Override
            public Function<Table<R, C, V>, ImmutableTable<R, C, V>> finisher() {
                return (table) -> {
                    ImmutableTable.Builder<R, C, V> builder = ImmutableTable
                            .builder();
                    return builder.putAll(table).build();
                };
            }

            @Override
            public Set<Characteristics> characteristics() {
                return CharacteristicSets.EMPTY;
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

    /**
     * @deprecated Use {@link ImmutableBiMap#toImmutableBiMap(Function, Function)} instead.
     */
    @Deprecated
    public static <T, K, U> Collector<T, ?, ImmutableBiMap<K, U>> toImmutableBiMap(
            Function<? super T, ? extends K> keyMapper,
            Function<? super T, ? extends U> valueMapper) {
        return toImmutableBiMap(keyMapper, valueMapper, throwingMerger());
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
                return (map) -> {
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

    /**
     * @deprecated Use {@link ImmutableMultiset#toImmutableMultiset()} instead.
     */
    @Deprecated
    public static <T> Collector<T, ?, ImmutableMultiset<T>> toImmutableMultiset() {
        return new MultisetCollector<T, ImmutableMultiset<T>>() {
            @Override
            public Function<Multiset<T>, ImmutableMultiset<T>> finisher() {
                return (set) -> {
                    ImmutableMultiset.Builder<T> builder = ImmutableMultiset.builder();
                    return builder.addAll(set).build();
                };
            }

            @Override
            public Set<Characteristics> characteristics() {
                return CharacteristicSets.EMPTY;
            }
        };
    }

    // TODO groupingBy
}
