package com.github.maximtereshchenko.bloom.domain;

import com.github.maximtereshchenko.bloom.api.port.Sound;

final class SoundTimer {

    private final Sound sound;
    private UnsignedByte value;

    SoundTimer(Sound sound) {
        this.sound = sound;
        this.value = UnsignedByte.ZERO;
    }

    synchronized void set(UnsignedByte value) {
        this.value = value;
        if (this.value.compareTo(UnsignedByte.ZERO) > 0) {
            sound.enable();
        }
    }

    synchronized void decrement() {
        value = value.difference(UnsignedByte.from(1));
    }
}
