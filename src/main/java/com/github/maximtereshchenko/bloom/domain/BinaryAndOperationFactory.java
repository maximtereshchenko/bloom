package com.github.maximtereshchenko.bloom.domain;

final class BinaryAndOperationFactory implements OperationFactory {

    private final Registers registers;

    BinaryAndOperationFactory(Registers registers) {
        this.registers = registers;
    }

    @Override
    public boolean supports(OperationCode operationCode) {
        return operationCode.firstNibble() == HexadecimalSymbol.EIGHT &&
               operationCode.lastNibble() == HexadecimalSymbol.TWO;
    }

    @Override
    public Operation supportedOperation(OperationCode operationCode) {
        return new BinaryAndOperation(
            registers,
            operationCode.middleLeftNibble(),
            operationCode.middleRightNibble()
        );
    }
}