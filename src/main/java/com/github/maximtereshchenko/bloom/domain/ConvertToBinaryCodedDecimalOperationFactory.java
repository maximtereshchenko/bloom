package com.github.maximtereshchenko.bloom.domain;

final class ConvertToBinaryCodedDecimalOperationFactory implements OperationFactory {

    private final Registers registers;
    private final RandomAccessMemory randomAccessMemory;

    ConvertToBinaryCodedDecimalOperationFactory(Registers registers, RandomAccessMemory randomAccessMemory) {
        this.registers = registers;
        this.randomAccessMemory = randomAccessMemory;
    }

    @Override
    public boolean supports(OperationCode operationCode) {
        return operationCode.firstNibble() == HexadecimalSymbol.F &&
            operationCode.middleRightNibble() == HexadecimalSymbol.THREE &&
            operationCode.lastNibble() == HexadecimalSymbol.THREE;
    }

    @Override
    public Operation supportedOperation(OperationCode operationCode) {
        return new ConvertToBinaryCodedDecimalOperation(
            registers,
            randomAccessMemory,
            operationCode.middleLeftNibble()
        );
    }
}
