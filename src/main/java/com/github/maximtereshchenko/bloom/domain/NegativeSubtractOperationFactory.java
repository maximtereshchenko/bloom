package com.github.maximtereshchenko.bloom.domain;

final class NegativeSubtractOperationFactory implements OperationFactory {

    private final Registers registers;

    NegativeSubtractOperationFactory(Registers registers) {
        this.registers = registers;
    }

    @Override
    public boolean supports(OperationCode operationCode) {
        return operationCode.firstNibble() == HexadecimalSymbol.EIGHT &&
            operationCode.lastNibble() == HexadecimalSymbol.SEVEN;
    }

    @Override
    public Operation supportedOperation(OperationCode operationCode) {
        return new NegativeSubtractOperation(
            registers,
            operationCode.middleLeftNibble(),
            operationCode.middleRightNibble()
        );
    }
}