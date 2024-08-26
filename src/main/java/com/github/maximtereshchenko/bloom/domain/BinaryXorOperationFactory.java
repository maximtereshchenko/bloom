package com.github.maximtereshchenko.bloom.domain;

final class BinaryXorOperationFactory implements OperationFactory {

    private final Registers registers;

    BinaryXorOperationFactory(Registers registers) {
        this.registers = registers;
    }

    @Override
    public boolean supports(OperationCode operationCode) {
        return operationCode.firstNibble() == HexadecimalSymbol.EIGHT &&
            operationCode.lastNibble() == HexadecimalSymbol.THREE;
    }

    @Override
    public Operation supportedOperation(OperationCode operationCode) {
        return new BinaryXorOperation(
            registers,
            operationCode.middleLeftNibble(),
            operationCode.middleRightNibble()
        );
    }
}