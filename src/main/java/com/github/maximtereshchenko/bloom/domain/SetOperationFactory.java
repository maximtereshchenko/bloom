package com.github.maximtereshchenko.bloom.domain;

final class SetOperationFactory implements OperationFactory {

    private final Registers registers;

    SetOperationFactory(Registers registers) {
        this.registers = registers;
    }

    @Override
    public boolean supports(OperationCode operationCode) {
        return operationCode.firstNibble() == HexadecimalSymbol.SIX;
    }

    @Override
    public Operation supportedOperation(OperationCode operationCode) {
        return new SetOperation(
            registers,
            operationCode.middleLeftNibble(),
            new Hexadecimal(
                operationCode.middleRightNibble(),
                operationCode.lastNibble()
            ).unsignedByte()
        );
    }
}
