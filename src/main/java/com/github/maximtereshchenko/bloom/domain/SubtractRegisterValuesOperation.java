package com.github.maximtereshchenko.bloom.domain;

/**
 * 8xy5 - SUB Vx, Vy. Set Vx = Vx - Vy, set VF = NOT borrow. If Vx > Vy, then VF is set to 1, otherwise 0. Then Vy is
 * subtracted from Vx, and the results stored in Vx.
 */
//TODO carry flag
final class SubtractRegisterValuesOperation implements Operation {

    private final HexadecimalSymbol firstRegisterName;
    private final HexadecimalSymbol secondRegisterName;

    SubtractRegisterValuesOperation(HexadecimalSymbol firstRegisterName, HexadecimalSymbol secondRegisterName) {
        this.firstRegisterName = firstRegisterName;
        this.secondRegisterName = secondRegisterName;
    }

    @Override
    public void execute(Registers registers, RandomAccessMemory randomAccessMemory, Stack stack, Display display) {
        var register = registers.generalPurpose(firstRegisterName);
        register.set(register.get().difference(registers.generalPurpose(secondRegisterName).get()));
    }
}
