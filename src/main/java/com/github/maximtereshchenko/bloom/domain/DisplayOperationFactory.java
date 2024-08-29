package com.github.maximtereshchenko.bloom.domain;

final class DisplayOperationFactory implements OperationFactory {

    private final Registers registers;
    private final RandomAccessMemory randomAccessMemory;
    private final StagingDisplay display;

    DisplayOperationFactory(
        Registers registers,
        RandomAccessMemory randomAccessMemory,
        StagingDisplay display
    ) {
        this.registers = registers;
        this.randomAccessMemory = randomAccessMemory;
        this.display = display;
    }

    @Override
    public boolean supports(OperationCode operationCode) {
        return operationCode.firstNibble() == HexadecimalSymbol.D;
    }

    @Override
    public Operation supportedOperation(OperationCode operationCode) {
        return new DisplayOperation(
            registers,
            randomAccessMemory,
            display,
            operationCode.middleRightNibble(),
            operationCode.middleLeftNibble(),
            operationCode.lastNibble().numericValue()
        );
    }
}
