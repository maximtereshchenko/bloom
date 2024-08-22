package com.github.maximtereshchenko.bloom.domain;

import java.util.Objects;

final class Byte {

    static final int LENGTH = 8;

    private final byte value;

    private Byte(byte value) {
        this.value = value;
    }

    Byte() {
        this((byte) 0);
    }

    static Byte from(int value) {
        return new Byte((byte) value);
    }

    static Byte from(String bits) {
        return new Byte((byte) Integer.parseInt(bits, 2));
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

    byte primitive() {
        return value;
    }

    boolean has(int index) {
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
        return Byte.from(value + other.primitive());
    }

    Byte or(Byte other) {
        return from(value | other.value);
    }
}
