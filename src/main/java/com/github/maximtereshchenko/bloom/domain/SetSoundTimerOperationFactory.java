package com.github.maximtereshchenko.bloom.domain;

final class SetSoundTimerOperationFactory implements OperationFactory {

    private final Registers registers;
    private final SoundTimer soundTimer;

    SetSoundTimerOperationFactory(Registers registers, SoundTimer soundTimer) {
        this.registers = registers;
        this.soundTimer = soundTimer;
    }

    @Override
    public boolean supports(OperationCode operationCode) {
        return operationCode.firstNibble() == HexadecimalSymbol.F &&
               operationCode.middleRightNibble() == HexadecimalSymbol.ONE &&
               operationCode.lastNibble() == HexadecimalSymbol.EIGHT;
    }

    @Override
    public Operation supportedOperation(OperationCode operationCode) {
        return new SetSoundTimerOperation(registers, soundTimer, operationCode.middleLeftNibble());
    }
}
