package com.github.maximtereshchenko.bloom;

import com.github.maximtereshchenko.bloom.api.port.Keypad;

final class FakeKeypad implements Keypad {

    @Override
    public boolean isPressed(char key) {
        return key == 'F';
    }

    @Override
    public char nextPressedKey() {
        return 'F';
    }
}
