package com.github.maximtereshchenko.bloom.domain;

final class DelayTimer {

    private UnsignedByte value = new UnsignedByte();

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
