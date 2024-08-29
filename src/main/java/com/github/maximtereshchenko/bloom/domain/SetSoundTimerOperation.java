package com.github.maximtereshchenko.bloom.domain;

/**
 * Fx18 - LD ST, Vx. Set sound timer = Vx. ST is set equal to the value of Vx.
 */
final class SetSoundTimerOperation implements Operation {

    private final Registers registers;
    private final SoundTimer soundTimer;
    private final HexadecimalSymbol registerName;

    SetSoundTimerOperation(
        Registers registers,
        SoundTimer soundTimer,
        HexadecimalSymbol registerName
    ) {
        this.registers = registers;
        this.soundTimer = soundTimer;
        this.registerName = registerName;
    }

    @Override
    public void execute() {
        soundTimer.set(registers.generalPurpose(registerName).get());
    }
}
