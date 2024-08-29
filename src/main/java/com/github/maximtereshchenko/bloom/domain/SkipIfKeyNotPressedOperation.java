package com.github.maximtereshchenko.bloom.domain;

import com.github.maximtereshchenko.bloom.api.port.Keypad;

/**
 * ExA1 - SKNP Vx. Skip next instruction if key with the value of Vx is not pressed. Checks the
 * keyboard, and if the key corresponding to the value of Vx is currently in the up position, PC
 * is increased by 2.
 */
class SkipIfKeyNotPressedOperation extends SkipIfKeyOperation {

    SkipIfKeyNotPressedOperation(
        Registers registers,
        Keypad keypad,
        HexadecimalSymbol registerName
    ) {
        super(registers, keypad, registerName);
    }

    @Override
    boolean shouldSkip() {
        return !isKeyPressed();
    }
}
