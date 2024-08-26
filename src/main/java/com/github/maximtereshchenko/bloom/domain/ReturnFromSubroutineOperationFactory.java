package com.github.maximtereshchenko.bloom.domain;

final class ReturnFromSubroutineOperationFactory implements OperationFactory {

    private final Registers registers;
    private final Stack stack;

    ReturnFromSubroutineOperationFactory(Registers registers, Stack stack) {
        this.registers = registers;
        this.stack = stack;
    }

    @Override
    public boolean supports(OperationCode operationCode) {
        return operationCode.firstNibble() == HexadecimalSymbol.ZERO &&
            operationCode.middleLeftNibble() == HexadecimalSymbol.ZERO &&
            operationCode.middleRightNibble() == HexadecimalSymbol.E &&
            operationCode.lastNibble() == HexadecimalSymbol.E;
    }

    @Override
    public Operation supportedOperation(OperationCode operationCode) {
        return new ReturnFromSubroutineOperation(registers, stack);
    }
}
