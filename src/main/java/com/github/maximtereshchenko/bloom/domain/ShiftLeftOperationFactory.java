package com.github.maximtereshchenko.bloom.domain;

final class ShiftLeftOperationFactory implements OperationFactory {

    private final Registers registers;

    ShiftLeftOperationFactory(Registers registers) {
        this.registers = registers;
    }

    @Override
    public boolean supports(OperationCode operationCode) {
        return operationCode.firstNibble() == HexadecimalSymbol.EIGHT &&
            operationCode.lastNibble() == HexadecimalSymbol.E;
    }

    @Override
    public Operation supportedOperation(OperationCode operationCode) {
        return new ShiftLeftOperation(registers, operationCode.middleLeftNibble());
    }
}