package com.github.maximtereshchenko.bloom.domain;

final class SkipIfRegisterValuesEqualOperationFactory implements OperationFactory {

    private final Registers registers;

    SkipIfRegisterValuesEqualOperationFactory(Registers registers) {
        this.registers = registers;
    }

    @Override
    public boolean supports(OperationCode operationCode) {
        return operationCode.firstNibble() == HexadecimalSymbol.FIVE &&
            operationCode.lastNibble() == HexadecimalSymbol.ZERO;
    }

    @Override
    public Operation supportedOperation(OperationCode operationCode) {
        return new SkipIfRegisterValuesEqualOperation(
            registers,
            operationCode.middleLeftNibble(),
            operationCode.middleRightNibble()
        );
    }
}