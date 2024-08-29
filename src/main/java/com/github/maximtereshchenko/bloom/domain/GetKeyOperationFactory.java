package com.github.maximtereshchenko.bloom.domain;

import com.github.maximtereshchenko.bloom.api.port.Keypad;

final class GetKeyOperationFactory implements OperationFactory {

    private final Registers registers;
    private final Keypad keypad;

    GetKeyOperationFactory(Registers registers, Keypad keypad) {
        this.registers = registers;
        this.keypad = keypad;
    }

    @Override
    public boolean supports(OperationCode operationCode) {
        return operationCode.firstNibble() == HexadecimalSymbol.F &&
               operationCode.middleRightNibble() == HexadecimalSymbol.ZERO &&
               operationCode.lastNibble() == HexadecimalSymbol.A;
    }

    @Override
    public Operation supportedOperation(OperationCode operationCode) {
        return new GetKeyOperation(registers, keypad, operationCode.middleLeftNibble());
    }
}