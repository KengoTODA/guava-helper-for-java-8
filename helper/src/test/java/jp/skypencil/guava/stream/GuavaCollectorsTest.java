package jp.skypencil.guava.stream;

import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;
import com.google.common.truth.Truth;

public class GuavaCollectorsTest {

    @Test
    public void testCollectToImmutableList() {
        ImmutableList<Integer> list = Stream.of(1, 2).collect(GuavaCollectors.toImmutableList());
        Truth.assertThat(list.get(0)).isEqualTo(1);
        Truth.assertThat(list.get(1)).isEqualTo(2);
        Truth.assertThat(list).hasSize(2);
    }

    @Test
    public void testCollectToImmutableSet() {
        ImmutableSet<Integer> set = Stream.of(1, 2).collect(GuavaCollectors.toImmutableSet());
        Truth.assertThat(set).containsAllOf(1, 2);
        Truth.assertThat(set).hasSize(2);
    }

    @Test
    public void testCollectToImmutableMap() {
        ImmutableMap<String, Integer> map = Stream.of(1, 2)
                .collect(GuavaCollectors.toImmutableMap(Object::toString, UnaryOperator.identity()));
        Truth.assertThat(map).containsEntry("1", 1);
        Truth.assertThat(map).containsEntry("2", 2);
        Truth.assertThat(map).hasSize(2);
    }

    @Test(expected = IllegalStateException.class)
    public void testDuplicatedMapEntry() {
        Stream.of(1, 1).collect(GuavaCollectors.toImmutableMap(Object::toString, UnaryOperator.identity()));
    }

    @Test
    public void testDuplicatedMapEntryWithMergeFunction() {
        ImmutableMap<String, Integer> map = Stream.of(1, 1).collect(
                GuavaCollectors.toImmutableMap(Object::toString,
                        UnaryOperator.identity(),
                        (existing, newValue) -> existing));
        Truth.assertThat(map).containsEntry("1", 1);
        Truth.assertThat(map).hasSize(1);
    }

    @Test
    public void testCollectToTable() {
        Table<String, String, Integer> table = Stream.of(10, 11).collect(
                GuavaCollectors.toTable(Object::toString, this::generateColumn, Function.identity()));
        Truth.assertThat(table).hasSize(2);
        Truth.assertThat(table).containsCell("10", "column", 10);
        Truth.assertThat(table).containsCell("11", "column", 11);
    }

    @Test
    public void testCollectToImmutableTable() {
        ImmutableTable<String, String, Integer> table = Stream.of(10, 11).collect(
                GuavaCollectors.toImmutableTable(Object::toString, this::generateColumn, Function.identity()));
        Truth.assertThat(table).hasSize(2);
        Truth.assertThat(table).containsCell("10", "column", 10);
        Truth.assertThat(table).containsCell("11", "column", 11);
    }

    @Test(expected = IllegalStateException.class)
    public void testDuplicatedTableCell() {
        Stream.of(10, 10).collect(GuavaCollectors.toTable(Object::toString, this::generateColumn, Function.identity()));
    }

    @Test(expected = IllegalStateException.class)
    public void testDuplicatedImmutableTableCell() {
        Stream.of(10, 10).collect(GuavaCollectors.toImmutableTable(Object::toString, this::generateColumn, Function.identity()));
    }

    @Test
    public void testDuplicatedTableCellWithMergeFunction() {
        Table<String, String, Integer> table = Stream.of(10, 10).collect(
                GuavaCollectors.toTable(Object::toString, this::generateColumn, Function.identity(),
                        (existing, newValue) -> existing));
        Truth.assertThat(table).containsCell("10", "column", 10);
        Truth.assertThat(table).hasSize(1);
    }

    @Test
    public void testDuplicatedImmutableTableCellWithMergeFunction() {
        ImmutableTable<String, String, Integer> table = Stream.of(10, 10).collect(
                GuavaCollectors.toImmutableTable(Object::toString, this::generateColumn, Function.identity(),
                        (existing, newValue) -> existing));
        Truth.assertThat(table).containsCell("10", "column", 10);
        Truth.assertThat(table).hasSize(1);
    }

    private String generateColumn(Object object) {
        return "column";
    }
}
