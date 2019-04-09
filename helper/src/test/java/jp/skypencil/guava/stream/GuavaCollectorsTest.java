package jp.skypencil.guava.stream;

import java.util.function.Function;
import java.util.stream.Stream;

import org.junit.Test;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import com.google.common.collect.Table;
import com.google.common.truth.Truth;

public class GuavaCollectorsTest {

    @Test
    public void testCollectToTable() {
        Table<String, String, Integer> table = Stream.of(10, 11).collect(
                GuavaCollectors.toTable(Object::toString, this::generateColumn, Function.identity()));
        Truth.assertThat(table).hasSize(2);
        Truth.assertThat(table).containsCell("10", "column", 10);
        Truth.assertThat(table).containsCell("11", "column", 11);
    }

    @Test(expected = IllegalStateException.class)
    public void testDuplicatedTableCell() {
        Stream.of(10, 10).collect(GuavaCollectors.toTable(Object::toString, this::generateColumn, Function.identity()));
    }

    @Test
    public void testDuplicatedTableCellWithMergeFunction() {
        Table<String, String, Integer> table = Stream.of(10, 10).collect(
                GuavaCollectors.toTable(Object::toString, this::generateColumn, Function.identity(),
                        (existing, newValue) -> existing));
        Truth.assertThat(table).containsCell("10", "column", 10);
        Truth.assertThat(table).hasSize(1);
    }

    private String generateColumn(Object object) {
        return "column";
    }

    @Test
    public void testCollectToBiMap() {
        BiMap<String, Integer> bimap = Stream.of(1, 2).collect(
                GuavaCollectors.toBiMap(Object::toString, Function.identity()));
        Truth.assertThat(bimap).hasSize(2);
        Truth.assertThat(bimap).containsEntry("1", 1);
        Truth.assertThat(bimap.inverse()).containsEntry(2, "2");
    }

    @Test(expected = IllegalStateException.class)
    public void testDuplicatedBiMapEntry() {
        Stream.of(10, 10).collect(
                GuavaCollectors.toBiMap(Object::toString, Function.identity()));
    }

    @Test
    public void testDuplicatedBiMapEntryWithMergeFunction() {
        BiMap<String, Integer> biMap = Stream.of(10, 10).collect(
                GuavaCollectors.toBiMap(Object::toString, Function.identity(),
                        (existing, newValue) -> existing));
        Truth.assertThat(biMap).containsEntry("10", 10);
        Truth.assertThat(biMap).hasSize(1);
    }

    @Test
    public void testDuplicatedImmutableBiMapEntryWithMergeFunction() {
        ImmutableBiMap<String, Integer> biMap = Stream.of(10, 10).collect(
                GuavaCollectors.toImmutableBiMap(Object::toString, Function.identity(),
                        (existing, newValue) -> existing));
        Truth.assertThat(biMap).containsEntry("10", 10);
        Truth.assertThat(biMap).hasSize(1);
    }

    @Test
    public void testCollectToMultimap() {
        Multimap<String, Integer> multimap = Stream.of(1, 2).collect(
                GuavaCollectors.toMultimap(Object::toString, Function.identity()));
        Truth.assertThat(multimap).hasSize(2);
        Truth.assertThat(multimap).containsEntry("1", 1);
        Truth.assertThat(multimap).containsEntry("2", 2);
    }

    @Test
    public void testDuplicatedMultimapEntry() {
        Multimap<String, Integer> multimap = Stream.of(10, 10).collect(
                GuavaCollectors.toMultimap(Object::toString, Function.identity()));
        Truth.assertThat(multimap).hasSize(2);
        Truth.assertThat(multimap.get("10")).hasSize(2);
    }

    @Test
    public void testCollectToImmutableMultimap() {
        ImmutableMultimap<String, Integer> multimap = Stream.of(1, 2).collect(
                GuavaCollectors.toImmutableMultimap(Object::toString, Function.identity()));
        Truth.assertThat(multimap).hasSize(2);
        Truth.assertThat(multimap).containsEntry("1", 1);
        Truth.assertThat(multimap).containsEntry("2", 2);
    }

    @Test
    public void testDuplicatedImmutableMultimapEntry() {
        ImmutableMultimap<String, Integer> multimap = Stream.of(10, 10).collect(
                GuavaCollectors.toImmutableMultimap(Object::toString, Function.identity()));
        Truth.assertThat(multimap).hasSize(2);
        Truth.assertThat(multimap.get("10")).hasSize(2);
    }

    @Test
    public void testCollectToMultiset() {
        Multiset<String> multiset = Stream.of(1, 2).map(Object::toString).collect(
                GuavaCollectors.toMultiset());
        Truth.assertThat(multiset).hasSize(2);
        Truth.assertThat(multiset).contains("1");
        Truth.assertThat(multiset).contains("2");
    }

    @Test
    public void testDuplicatedMultisetEntry() {
        Multiset<String> multiset = Stream.of(10, 10).map(Object::toString).collect(
                GuavaCollectors.toMultiset());
        Truth.assertThat(multiset).hasSize(2);
        Truth.assertThat(multiset.count("10")).isEqualTo(2);
    }
}
