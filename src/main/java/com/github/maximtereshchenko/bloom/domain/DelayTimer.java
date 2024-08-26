package com.github.maximtereshchenko.bloom.domain;

final class DelayTimer {

    private UnsignedByte value = UnsignedByte.ZERO;

    synchronized void set(UnsignedByte value) {
        this.value = value;
    }

    synchronized UnsignedByte get() {
        return value;
    }

    synchronized void decrement() {
        value = value.difference(UnsignedByte.from(1));
    }
}
