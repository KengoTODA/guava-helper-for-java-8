package jp.skypencil.guava.stream;

import java.util.stream.Stream;

import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.truth.Truth;

public class GuavaCollectorsTest {

    @Test
    public void testCollectToImmutableList() {
        ImmutableList<Integer> list = Stream.of(1, 2).collect(GuavaCollectors.toImmutableList());
        Truth.assertThat(list.get(0)).isEqualTo(1);
        Truth.assertThat(list.get(1)).isEqualTo(2);
    }

}
