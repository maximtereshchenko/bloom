package com.github.maximtereshchenko.bloom.domain;

final class SkipIfSingleRegisterValueNotEqualOperationFactory implements OperationFactory {

    private final Registers registers;

    SkipIfSingleRegisterValueNotEqualOperationFactory(Registers registers) {
        this.registers = registers;
    }

    @Override
    public boolean supports(OperationCode operationCode) {
        return operationCode.firstNibble() == HexadecimalSymbol.FOUR;
    }

    @Override
    public Operation supportedOperation(OperationCode operationCode) {
        return new SkipIfSingleRegisterValueNotEqualOperation(
            registers,
            operationCode.middleLeftNibble(),
            new Hexadecimal(
                operationCode.middleRightNibble(),
                operationCode.lastNibble()
            ).unsignedByte()
        );
    }
}