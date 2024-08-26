package com.github.maximtereshchenko.bloom.domain;

final class SetDelayTimerOperationFactory implements OperationFactory {

    private final Registers registers;
    private final DelayTimer delayTimer;

    SetDelayTimerOperationFactory(Registers registers, DelayTimer delayTimer) {
        this.registers = registers;
        this.delayTimer = delayTimer;
    }

    @Override
    public boolean supports(OperationCode operationCode) {
        return operationCode.firstNibble() == HexadecimalSymbol.F &&
            operationCode.middleRightNibble() == HexadecimalSymbol.ONE &&
            operationCode.lastNibble() == HexadecimalSymbol.FIVE;
    }

    @Override
    public Operation supportedOperation(OperationCode operationCode) {
        return new SetDelayTimerOperation(registers, delayTimer, operationCode.middleLeftNibble());
    }
}
