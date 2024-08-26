package com.github.maximtereshchenko.bloom.domain;

final class AddToIndexOperationFactory implements OperationFactory {

    private final Registers registers;

    AddToIndexOperationFactory(Registers registers) {
        this.registers = registers;
    }

    @Override
    public boolean supports(OperationCode operationCode) {
        return operationCode.firstNibble() == HexadecimalSymbol.F &&
            operationCode.middleRightNibble() == HexadecimalSymbol.ONE &&
            operationCode.lastNibble() == HexadecimalSymbol.E;
    }

    @Override
    public Operation supportedOperation(OperationCode operationCode) {
        return new AddToIndexOperation(registers, operationCode.middleLeftNibble());
    }
}
