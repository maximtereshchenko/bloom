package com.github.maximtereshchenko.bloom.domain;

import java.util.concurrent.atomic.AtomicReference;

final class DelayTimer {

    private final AtomicReference<UnsignedByte> reference = new AtomicReference<>(new UnsignedByte());

    void set(UnsignedByte value) {
        reference.set(value);
    }

    UnsignedByte get() {
        return reference.get();
    }
}
