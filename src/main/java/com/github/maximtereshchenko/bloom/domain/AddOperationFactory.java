package com.github.maximtereshchenko.bloom.domain;

final class AddOperationFactory implements OperationFactory {

    private final Registers registers;

    AddOperationFactory(Registers registers) {
        this.registers = registers;
    }

    @Override
    public boolean supports(OperationCode operationCode) {
        return operationCode.firstNibble() == HexadecimalSymbol.SEVEN;
    }

    @Override
    public Operation supportedOperation(OperationCode operationCode) {
        return new AddOperation(
            registers,
            operationCode.middleLeftNibble(),
            new Hexadecimal(
                operationCode.middleRightNibble(),
                operationCode.lastNibble()
            ).unsignedByte()
        );
    }
}
