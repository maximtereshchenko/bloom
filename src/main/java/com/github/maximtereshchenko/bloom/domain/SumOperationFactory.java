package com.github.maximtereshchenko.bloom.domain;

final class SumOperationFactory implements OperationFactory {

    private final Registers registers;

    SumOperationFactory(Registers registers) {
        this.registers = registers;
    }

    @Override
    public boolean supports(OperationCode operationCode) {
        return operationCode.firstNibble() == HexadecimalSymbol.EIGHT &&
            operationCode.lastNibble() == HexadecimalSymbol.FOUR;
    }

    @Override
    public Operation supportedOperation(OperationCode operationCode) {
        return new SumOperation(registers, operationCode.middleLeftNibble(), operationCode.middleRightNibble());
    }
}