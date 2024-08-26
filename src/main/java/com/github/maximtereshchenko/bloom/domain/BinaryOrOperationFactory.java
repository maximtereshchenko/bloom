package com.github.maximtereshchenko.bloom.domain;

final class BinaryOrOperationFactory implements OperationFactory {

    private final Registers registers;

    BinaryOrOperationFactory(Registers registers) {
        this.registers = registers;
    }

    @Override
    public boolean supports(OperationCode operationCode) {
        return operationCode.firstNibble() == HexadecimalSymbol.EIGHT &&
            operationCode.lastNibble() == HexadecimalSymbol.ONE;
    }

    @Override
    public Operation supportedOperation(OperationCode operationCode) {
        return new BinaryOrOperation(registers, operationCode.middleLeftNibble(), operationCode.middleRightNibble());
    }
}
