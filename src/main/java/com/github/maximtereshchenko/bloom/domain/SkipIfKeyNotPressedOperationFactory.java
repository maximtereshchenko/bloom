package com.github.maximtereshchenko.bloom.domain;

import com.github.maximtereshchenko.bloom.api.port.Keypad;

final class SkipIfKeyNotPressedOperationFactory implements OperationFactory {

    private final Registers registers;
    private final Keypad keypad;

    SkipIfKeyNotPressedOperationFactory(Registers registers, Keypad keypad) {
        this.registers = registers;
        this.keypad = keypad;
    }

    @Override
    public boolean supports(OperationCode operationCode) {
        return operationCode.firstNibble() == HexadecimalSymbol.E &&
            operationCode.middleRightNibble() == HexadecimalSymbol.A &&
            operationCode.lastNibble() == HexadecimalSymbol.ONE;
    }

    @Override
    public Operation supportedOperation(OperationCode operationCode) {
        return new SkipIfKeyNotPressedOperation(registers, keypad, operationCode.middleLeftNibble());
    }
}