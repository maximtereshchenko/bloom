package com.github.maximtereshchenko.bloom.domain;

import com.github.maximtereshchenko.bloom.api.port.Keypad;

/**
 * Fx0A - LD Vx, K. Wait for a key press, store the value of the key in Vx. All execution stops
 * until a key is pressed, then the value of that key is stored in Vx.
 */
final class GetKeyOperation implements Operation {

    private final Registers registers;
    private final Keypad keypad;
    private final HexadecimalSymbol registerName;

    GetKeyOperation(Registers registers, Keypad keypad, HexadecimalSymbol registerName) {
        this.registers = registers;
        this.keypad = keypad;
        this.registerName = registerName;
    }

    @Override
    public void execute() {
        registers.generalPurpose(registerName).set(HexadecimalSymbol.from(keypad.nextPressedKey()).numericValue());
    }
}
