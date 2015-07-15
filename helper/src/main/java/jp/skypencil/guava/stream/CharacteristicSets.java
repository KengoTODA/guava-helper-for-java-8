package jp.skypencil.guava.stream;

import java.util.EnumSet;
import java.util.stream.Collector.Characteristics;

final class CharacteristicSets {
    static final EnumSet<Characteristics> EMPTY = EnumSet
            .noneOf(java.util.stream.Collector.Characteristics.class);

    static final EnumSet<Characteristics> IDENTITY = EnumSet
            .of(Characteristics.IDENTITY_FINISH);
}
