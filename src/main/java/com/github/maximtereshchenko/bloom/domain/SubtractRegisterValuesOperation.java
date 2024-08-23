package com.github.maximtereshchenko.bloom.domain;

/**
 * 8xy5 - SUB Vx, Vy. Set Vx = Vx - Vy, set VF = NOT borrow. If Vx > Vy, then VF is set to 1, otherwise 0. Then Vy is
 * subtracted from Vx, and the results stored in Vx.
 */
final class SubtractRegisterValuesOperation implements Operation {

    private final HexadecimalSymbol firstRegisterName;
    private final HexadecimalSymbol secondRegisterName;

    SubtractRegisterValuesOperation(HexadecimalSymbol firstRegisterName, HexadecimalSymbol secondRegisterName) {
        this.firstRegisterName = firstRegisterName;
        this.secondRegisterName = secondRegisterName;
    }

    @Override
    public void execute(Registers registers, RandomAccessMemory randomAccessMemory, Stack stack, Display display) {
        var flagRegister = registers.flagRegister();
        flagRegister.disable();
        var register = registers.generalPurpose(firstRegisterName);
        var first = register.get();
        var second = registers.generalPurpose(secondRegisterName).get();
        if (first.compareTo(second) > 0) {
            flagRegister.enable();
        }
        register.set(first.difference(second));
    }
}
