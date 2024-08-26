package com.github.maximtereshchenko.bloom.domain;

final class SetFontCharacterOperationFactory implements OperationFactory {

    private final Registers registers;
    private final RandomAccessMemory randomAccessMemory;

    SetFontCharacterOperationFactory(Registers registers, RandomAccessMemory randomAccessMemory) {
        this.registers = registers;
        this.randomAccessMemory = randomAccessMemory;
    }

    @Override
    public boolean supports(OperationCode operationCode) {
        return operationCode.firstNibble() == HexadecimalSymbol.F &&
            operationCode.middleRightNibble() == HexadecimalSymbol.TWO
            && operationCode.lastNibble() == HexadecimalSymbol.NINE;
    }

    @Override
    public Operation supportedOperation(OperationCode operationCode) {
        return new SetFontCharacterOperation(registers, randomAccessMemory, operationCode.middleLeftNibble());
    }
}
