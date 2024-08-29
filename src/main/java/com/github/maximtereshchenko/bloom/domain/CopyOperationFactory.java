package com.github.maximtereshchenko.bloom.domain;

final class CopyOperationFactory implements OperationFactory {

    private final Registers registers;

    CopyOperationFactory(Registers registers) {
        this.registers = registers;
    }

    @Override
    public boolean supports(OperationCode operationCode) {
        return operationCode.firstNibble() == HexadecimalSymbol.EIGHT &&
               operationCode.lastNibble() == HexadecimalSymbol.ZERO;
    }

    @Override
    public Operation supportedOperation(OperationCode operationCode) {
        return new CopyOperation(
            registers,
            operationCode.middleRightNibble(),
            operationCode.middleLeftNibble()
        );
    }
}
