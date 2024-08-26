package com.github.maximtereshchenko.bloom.domain;

/**
 * Fx07 - LD Vx, DT. Set Vx = delay timer value. The value of DT is placed into Vx.
 */
final class ReadDelayTimerOperation implements Operation {

    private final Registers registers;
    private final DelayTimer delayTimer;
    private final HexadecimalSymbol registerName;

    ReadDelayTimerOperation(Registers registers, DelayTimer delayTimer, HexadecimalSymbol registerName) {
        this.registers = registers;
        this.delayTimer = delayTimer;
        this.registerName = registerName;
    }

    @Override
    public void execute() {
        registers.generalPurpose(registerName).set(delayTimer.get());
    }
}
