package com.github.maximtereshchenko.bloom.domain;

import java.util.stream.Stream;

enum HexadecimalSymbol {

    ZERO,
    ONE,
    TWO,
    THREE,
    FOUR,
    FIVE,
    SIX,
    SEVEN,
    EIGHT,
    NINE,
    A,
    B,
    C,
    D,
    E,
    F;

    static HexadecimalSymbol from(char character) {
        return Stream.of(values())
            .filter(symbol -> symbol.primitive() == character)
            .findAny()
            .orElseThrow(() -> new IllegalArgumentException(String.valueOf(character)));
    }

    char primitive() {
        var ordinal = ordinal();
        if (ordinal <= 9) {
            return (char) ('0' + ordinal);
        }
        return (char) ('A' + ordinal - 10);
    }

    int numericValue() {
        return Character.getNumericValue(primitive());
    }
}
