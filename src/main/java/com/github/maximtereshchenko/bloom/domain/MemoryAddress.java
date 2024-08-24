package com.github.maximtereshchenko.bloom.domain;

final class MemoryAddress {

    private static final int PRIMITIVE_MAX = 0xFFF;

    private final short value;

    private MemoryAddress(short value) {
        this.value = value;
    }

    static MemoryAddress from(int value) {
        return new MemoryAddress((short) (value & PRIMITIVE_MAX));
    }

    short primitive() {
        return value;
    }

    MemoryAddress withOffset(UnsignedByte offset) {
        return withOffset(offset.primitive());
    }

    MemoryAddress withOffset(int offset) {
        return MemoryAddress.from(value + offset);
    }

    MemoryAddress next() {
        return withOffset(1);
    }
}
