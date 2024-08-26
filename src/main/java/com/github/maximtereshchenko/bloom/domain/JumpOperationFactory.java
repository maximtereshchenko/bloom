package com.github.maximtereshchenko.bloom.domain;

final class JumpOperationFactory implements OperationFactory {

    private final Registers registers;

    JumpOperationFactory(Registers registers) {
        this.registers = registers;
    }

    @Override
    public boolean supports(OperationCode operationCode) {
        return operationCode.firstNibble() == HexadecimalSymbol.ONE;
    }

    @Override
    public Operation supportedOperation(OperationCode operationCode) {
        return new JumpOperation(
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
