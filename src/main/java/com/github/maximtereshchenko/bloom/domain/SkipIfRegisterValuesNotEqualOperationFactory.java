package com.github.maximtereshchenko.bloom.domain;

final class SkipIfRegisterValuesNotEqualOperationFactory implements OperationFactory {

    private final Registers registers;

    SkipIfRegisterValuesNotEqualOperationFactory(Registers registers) {
        this.registers = registers;
    }

    @Override
    public boolean supports(OperationCode operationCode) {
        return operationCode.firstNibble() == HexadecimalSymbol.NINE &&
               operationCode.lastNibble() == HexadecimalSymbol.ZERO;
    }

    @Override
    public Operation supportedOperation(OperationCode operationCode) {
        return new SkipIfRegisterValuesNotEqualOperation(
            registers,
            operationCode.middleLeftNibble(),
            operationCode.middleRightNibble()
        );
    }
}