package com.github.maximtereshchenko.bloom.domain;

final class StoreOperationFactory implements OperationFactory {

    private final Registers registers;
    private final RandomAccessMemory randomAccessMemory;

    StoreOperationFactory(Registers registers, RandomAccessMemory randomAccessMemory) {
        this.registers = registers;
        this.randomAccessMemory = randomAccessMemory;
    }

    @Override
    public boolean supports(OperationCode operationCode) {
        return operationCode.firstNibble() == HexadecimalSymbol.F &&
               operationCode.middleRightNibble() == HexadecimalSymbol.FIVE &&
               operationCode.lastNibble() == HexadecimalSymbol.FIVE;
    }

    @Override
    public Operation supportedOperation(OperationCode operationCode) {
        return new StoreOperation(registers, randomAccessMemory, operationCode.middleLeftNibble());
    }
}