package com.github.maximtereshchenko.bloom.domain;

final class SubtractOperationFactory implements OperationFactory {

    private final Registers registers;

    SubtractOperationFactory(Registers registers) {
        this.registers = registers;
    }

    @Override
    public boolean supports(OperationCode operationCode) {
        return operationCode.firstNibble() == HexadecimalSymbol.EIGHT &&
            operationCode.lastNibble() == HexadecimalSymbol.FIVE;
    }

    @Override
    public Operation supportedOperation(OperationCode operationCode) {
        return new SubtractOperation(registers, operationCode.middleLeftNibble(), operationCode.middleRightNibble());
    }
}