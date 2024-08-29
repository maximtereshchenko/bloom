package com.github.maximtereshchenko.bloom.domain;

final class LoadOperationFactory implements OperationFactory {

    private final Registers registers;
    private final RandomAccessMemory randomAccessMemory;

    LoadOperationFactory(Registers registers, RandomAccessMemory randomAccessMemory) {
        this.registers = registers;
        this.randomAccessMemory = randomAccessMemory;
    }

    @Override
    public boolean supports(OperationCode operationCode) {
        return operationCode.firstNibble() == HexadecimalSymbol.F &&
               operationCode.middleRightNibble() == HexadecimalSymbol.SIX &&
               operationCode.lastNibble() == HexadecimalSymbol.FIVE;
    }

    @Override
    public Operation supportedOperation(OperationCode operationCode) {
        return new LoadOperation(registers, randomAccessMemory, operationCode.middleLeftNibble());
    }
}