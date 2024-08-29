package com.github.maximtereshchenko.bloom.domain;

import com.github.maximtereshchenko.bloom.api.port.Keypad;

/**
 * Ex9E - SKP Vx. Skip next instruction if key with the value of Vx is pressed. Checks the
 * keyboard, and if the key corresponding to the value of Vx is currently in the down position,
 * PC is increased by 2.
 */
class SkipIfKeyPressedOperation extends SkipIfKeyOperation {

    SkipIfKeyPressedOperation(Registers registers, Keypad keypad, HexadecimalSymbol registerName) {
        super(registers, keypad, registerName);
    }

    @Override
    boolean shouldSkip() {
        return isKeyPressed();
    }
}
