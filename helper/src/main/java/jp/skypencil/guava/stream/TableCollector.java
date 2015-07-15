package jp.skypencil.guava.stream;

import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

abstract class TableCollector<T, R, C, V, E> implements Collector<T, Table<R, C, V>, E> {
    private final Function<? super T, ? extends R> rowMapper;
    private final Function<? super T, ? extends C> columnMapper;
    private final Function<? super T, ? extends V> valueMapper;
    private final BinaryOperator<V> mergeFunction;

    TableCollector(
        Function<? super T, ? extends R> rowMapper,
        Function<? super T, ? extends C> columnMapper,
        Function<? super T, ? extends V> valueMapper,
        BinaryOperator<V> mergeFunction) {
        this.rowMapper = rowMapper;
        this.columnMapper = columnMapper;
        this.valueMapper = valueMapper;
        this.mergeFunction = mergeFunction;
    }

    @Override
    public Supplier<Table<R, C, V>> supplier() {
        return HashBasedTable::create;
    }

    @Override
    public BiConsumer<Table<R, C, V>, T> accumulator() {
        return (table, data) -> {
            R row = rowMapper.apply(data);
            C column = columnMapper.apply(data);
            V value = valueMapper.apply(data);

            if (table.contains(row, column)) {
                V existing = table.get(row, column);
                table.put(row, column, mergeFunction.apply(existing, value));
            } else {
                table.put(row, column, value);
            }
        };
    }

    @Override
    public BinaryOperator<Table<R, C, V>> combiner() {
        return (table, another) -> {
            another.cellSet().forEach(cell -> {
                R row = cell.getRowKey();
                C column = cell.getColumnKey();
                V value = cell.getValue();

                if (table.contains(row, column)) {
                    V existing = table.get(row, column);
                    table.put(row, column, mergeFunction.apply(existing, value));
                } else {
                    table.put(row, column, value);
                }
            });
            return table;
        };
    }
}