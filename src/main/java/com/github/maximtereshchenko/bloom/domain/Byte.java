package com.github.maximtereshchenko.bloom.domain;

import java.util.Objects;

final class Byte implements Comparable<Byte> {

    static final int LENGTH = 8;

    private final int value;

    private Byte(int value) {
        this.value = value;
    }

    Byte() {
        this(0);
    }

    static Byte from(int value) {
        return new Byte(value & 0xFF);
    }

    static Byte from(String bits) {
        return from(Integer.parseInt(bits, 2));
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        return object instanceof Byte other &&
            value == other.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public int compareTo(Byte other) {
        return Integer.compare(value, other.value);
    }

    int primitive() {
        return value;
    }

    boolean hasBit(int index) {
        return (value & (1 << LENGTH - index - 1)) != 0;
    }

    Hexadecimal hexadecimal() {
        var string = "%02X".formatted(value);
        return new Hexadecimal(
            HexadecimalSymbol.from(string.charAt(0)),
            HexadecimalSymbol.from(string.charAt(1))
        );
    }

    Byte sum(Byte other) {
        return Byte.from(value + other.value);
    }

    Byte difference(Byte other) {
        return Byte.from(value - other.value);
    }

    Byte or(Byte other) {
        return from(value | other.value);
    }

    Byte and(Byte other) {
        return from(value & other.value);
    }

    Byte xor(Byte other) {
        return from(value ^ other.value);
    }
}
