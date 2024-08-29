package com.github.maximtereshchenko.bloom.domain;

final class ReadDelayTimerOperationFactory implements OperationFactory {

    private final Registers registers;
    private final DelayTimer delayTimer;

    ReadDelayTimerOperationFactory(Registers registers, DelayTimer delayTimer) {
        this.registers = registers;
        this.delayTimer = delayTimer;
    }

    @Override
    public boolean supports(OperationCode operationCode) {
        return operationCode.firstNibble() == HexadecimalSymbol.F &&
               operationCode.middleRightNibble() == HexadecimalSymbol.ZERO &&
               operationCode.lastNibble() == HexadecimalSymbol.SEVEN;
    }

    @Override
    public Operation supportedOperation(OperationCode operationCode) {
        return new ReadDelayTimerOperation(registers, delayTimer, operationCode.middleLeftNibble());
    }
}
