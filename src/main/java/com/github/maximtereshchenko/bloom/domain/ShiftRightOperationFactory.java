package com.github.maximtereshchenko.bloom.domain;

final class ShiftRightOperationFactory implements OperationFactory {

    private final Registers registers;

    ShiftRightOperationFactory(Registers registers) {
        this.registers = registers;
    }

    @Override
    public boolean supports(OperationCode operationCode) {
        return operationCode.firstNibble() == HexadecimalSymbol.EIGHT &&
            operationCode.lastNibble() == HexadecimalSymbol.SIX;
    }

    @Override
    public Operation supportedOperation(OperationCode operationCode) {
        return new ShiftRightOperation(registers, operationCode.middleLeftNibble());
    }
}