package com.github.maximtereshchenko.bloom.domain;

final class JumpWithOffsetOperationFactory implements OperationFactory {

    private final Registers registers;

    JumpWithOffsetOperationFactory(Registers registers) {
        this.registers = registers;
    }

    @Override
    public boolean supports(OperationCode operationCode) {
        return operationCode.firstNibble() == HexadecimalSymbol.B;
    }

    @Override
    public Operation supportedOperation(OperationCode operationCode) {
        return new JumpWithOffsetOperation(
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
