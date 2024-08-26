package com.github.maximtereshchenko.bloom.domain;

final class SkipIfSingleRegisterValueEqualOperationFactory implements OperationFactory {

    private final Registers registers;

    SkipIfSingleRegisterValueEqualOperationFactory(Registers registers) {
        this.registers = registers;
    }

    @Override
    public boolean supports(OperationCode operationCode) {
        return operationCode.firstNibble() == HexadecimalSymbol.THREE;
    }

    @Override
    public Operation supportedOperation(OperationCode operationCode) {
        return new SkipIfSingleRegisterValueEqualOperation(
            registers,
            operationCode.middleLeftNibble(),
            new Hexadecimal(operationCode.middleRightNibble(), operationCode.lastNibble()).unsignedByte()
        );
    }
}