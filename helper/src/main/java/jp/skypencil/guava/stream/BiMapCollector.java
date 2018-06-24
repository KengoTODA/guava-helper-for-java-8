package jp.skypencil.guava.stream;

import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

abstract class BiMapCollector<T, K, U, R extends BiMap<K, U>>
        implements Collector<T, BiMap<K, U>, R> {
    private final BinaryOperator<U> mergeFunction;
    private final Function<? super T, ? extends U> valueMapper;
    private final Function<? super T, ? extends K> keyMapper;

    BiMapCollector(Function<? super T, ? extends K> keyMapper,
            Function<? super T, ? extends U> valueMapper,
            BinaryOperator<U> mergeFunction) {
        this.keyMapper = keyMapper;
        this.valueMapper = valueMapper;
        this.mergeFunction = mergeFunction;
    }

    @Override
    public Supplier<BiMap<K, U>> supplier() {
        return HashBiMap::create;
    }

    @Override
    public BiConsumer<BiMap<K, U>, T> accumulator() {
        return (map, data) -> {
            K key = keyMapper.apply(data);
            U value = valueMapper.apply(data);
            map.merge(key, value, mergeFunction);
        };
    }

    @Override
    public BinaryOperator<BiMap<K, U>> combiner() {
        return (map, another) -> {
            another.forEach((key, value) ->
                map.merge(key, value, mergeFunction)
            );
            return map;
        };
    }
}
