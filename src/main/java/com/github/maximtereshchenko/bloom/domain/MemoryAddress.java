package com.github.maximtereshchenko.bloom.domain;

final class MemoryAddress {

    private final short value;

    private MemoryAddress(short value) {
        this.value = value;
    }

    static MemoryAddress from(int value) {
        return new MemoryAddress((short) value);
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
