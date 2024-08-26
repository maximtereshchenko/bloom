package com.github.maximtereshchenko.bloom.domain;

final class SetIndexOperationFactory implements OperationFactory {

    private final Registers registers;

    SetIndexOperationFactory(Registers registers) {
        this.registers = registers;
    }

    @Override
    public boolean supports(OperationCode operationCode) {
        return operationCode.firstNibble() == HexadecimalSymbol.A;
    }

    @Override
    public Operation supportedOperation(OperationCode operationCode) {
        return new SetIndexOperation(
            registers,
            new Hexadecimal(
                operationCode.middleLeftNibble(),
                operationCode.middleRightNibble(),
                operationCode.lastNibble()
            )
                .memoryAddress()
        );
    }
}
