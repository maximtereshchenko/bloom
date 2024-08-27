package com.github.maximtereshchenko.bloom.domain;

import com.github.maximtereshchenko.bloom.api.port.Sound;

final class SoundTimer extends Timer {

    private final Sound sound;

    SoundTimer(Sound sound) {
        this.sound = sound;
    }

    @Override
    void onCountDownStart(UnsignedByte start) {
        sound.enable();
    }

    @Override
    void onCountDownEnd() {
        sound.disable();
    }
}
