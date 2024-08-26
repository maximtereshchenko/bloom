package com.github.maximtereshchenko.bloom.domain;

import com.github.maximtereshchenko.bloom.api.port.Keypad;

abstract class SkipIfKeyOperation extends SkipConditionallyOperation {

    private final Registers registers;
    private final Keypad keypad;
    private final HexadecimalSymbol registerName;

    SkipIfKeyOperation(Registers registers, Keypad keypad, HexadecimalSymbol registerName) {
        super(registers);
        this.registers = registers;
        this.keypad = keypad;
        this.registerName = registerName;
    }

    boolean isKeyPressed() {
        return keypad.isPressed(registers.generalPurpose(registerName).get().hexadecimal().last().primitive());
    }
}
