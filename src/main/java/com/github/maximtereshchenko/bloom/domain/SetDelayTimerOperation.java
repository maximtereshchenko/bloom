package com.github.maximtereshchenko.bloom.domain;

/**
 * Fx15 - LD DT, Vx. Set delay timer = Vx. DT is set equal to the value of Vx.
 */
final class SetDelayTimerOperation implements Operation {

    private final Registers registers;
    private final DelayTimer delayTimer;
    private final HexadecimalSymbol registerName;

    SetDelayTimerOperation(Registers registers, DelayTimer delayTimer, HexadecimalSymbol registerName) {
        this.registers = registers;
        this.delayTimer = delayTimer;
        this.registerName = registerName;
    }

    @Override
    public void execute() {
        delayTimer.set(registers.generalPurpose(registerName).get());
    }
}
