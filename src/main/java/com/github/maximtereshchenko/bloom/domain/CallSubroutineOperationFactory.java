package com.github.maximtereshchenko.bloom.domain;

final class CallSubroutineOperationFactory implements OperationFactory {

    private final Registers registers;
    private final Stack stack;

    CallSubroutineOperationFactory(Registers registers, Stack stack) {
        this.registers = registers;
        this.stack = stack;
    }

    @Override
    public boolean supports(OperationCode operationCode) {
        return operationCode.firstNibble() == HexadecimalSymbol.TWO;
    }

    @Override
    public Operation supportedOperation(OperationCode operationCode) {
        return new CallSubroutineOperation(
            registers,
            stack,
            new Hexadecimal(
                operationCode.middleLeftNibble(),
                operationCode.middleRightNibble(),
                operationCode.lastNibble()
            )
                .memoryAddress()
        );
    }
}
