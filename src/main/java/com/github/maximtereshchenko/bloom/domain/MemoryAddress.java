package com.github.maximtereshchenko.bloom.domain;

final class MemoryAddress {

    private final short value;

    private MemoryAddress(short value) {
        this.value = value;
    }

    static MemoryAddress from(int value) {
        var casted = (short) value;
        if (casted != value) {
            throw new IllegalArgumentException(String.valueOf(value));
        }
        return new MemoryAddress(casted);
    }

    short value() {
        return value;
    }

    MemoryAddress withOffset(int offset) {
        return MemoryAddress.from(value + offset);
    }

    MemoryAddress next() {
        return withOffset(1);
    }
}
