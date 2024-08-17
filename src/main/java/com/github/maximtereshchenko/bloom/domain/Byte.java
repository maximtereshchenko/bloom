package com.github.maximtereshchenko.bloom.domain;

final class Byte {

    static final int LENGTH = 8;

    private final byte value;

    Byte(byte value) {
        this.value = value;
    }

    Byte() {
        this((byte) 0);
    }

    static Byte from(String bits) {
        return new Byte((byte) Integer.parseInt(bits, 2));
    }

    byte value() {
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
}
