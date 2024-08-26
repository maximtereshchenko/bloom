package com.github.maximtereshchenko.bloom.domain;

import com.github.maximtereshchenko.bloom.api.port.Keypad;

final class SkipIfKeyPressedOperationFactory implements OperationFactory {

    private final Registers registers;
    private final Keypad keypad;

    SkipIfKeyPressedOperationFactory(Registers registers, Keypad keypad) {
        this.registers = registers;
        this.keypad = keypad;
    }

    @Override
    public boolean supports(OperationCode operationCode) {
        return operationCode.firstNibble() == HexadecimalSymbol.E &&
            operationCode.middleRightNibble() == HexadecimalSymbol.NINE &&
            operationCode.lastNibble() == HexadecimalSymbol.E;
    }

    @Override
    public Operation supportedOperation(OperationCode operationCode) {
        return new SkipIfKeyPressedOperation(registers, keypad, operationCode.middleLeftNibble());
    }
}