package com.github.maximtereshchenko.bloom.domain;

import com.github.maximtereshchenko.bloom.api.port.Keypad;

/**
 * Ex9E - SKP Vx. Skip next instruction if key with the value of Vx is pressed. Checks the keyboard, and if the key
 * corresponding to the value of Vx is currently in the down position, PC is increased by 2.
 */
class SkipIfKeyPressedOperation extends SkipConditionallyOperation {

    private final Registers registers;
    private final Keypad keypad;
    private final HexadecimalSymbol registerName;

    SkipIfKeyPressedOperation(Registers registers, Keypad keypad, HexadecimalSymbol registerName) {
        super(registers);
        this.registers = registers;
        this.keypad = keypad;
        this.registerName = registerName;
    }

    @Override
    boolean shouldSkip() {
        return keypad.isPressed(registers.generalPurpose(registerName).get().hexadecimal().last().primitive());
    }
}
