package com.github.maximtereshchenko.bloom;

import com.github.maximtereshchenko.bloom.api.port.Sound;

final class FakeSound implements Sound {

    private boolean enabled = false;

    @Override
    public void enable() {
        enabled = true;
    }

    @Override
    public void disable() {
        enabled = false;
    }

    boolean isEnabled() {
        return enabled;
    }
}
