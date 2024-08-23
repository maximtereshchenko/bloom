package com.github.maximtereshchenko.bloom.domain;

/**
 * 8xy4 - ADD Vx, Vy. Set Vx = Vx + Vy, set VF = carry. The values of Vx and Vy are added together. If the result is
 * greater than 8 bits (i.e., > 255) VF is set to 1, otherwise 0. Only the lowest 8 bits of the result are kept, and
 * stored in Vx.
 */
//TODO carry flag
final class SumRegisterValuesOperation implements Operation {

    private final HexadecimalSymbol firstRegisterName;
    private final HexadecimalSymbol secondRegisterName;

    SumRegisterValuesOperation(HexadecimalSymbol firstRegisterName, HexadecimalSymbol secondRegisterName) {
        this.firstRegisterName = firstRegisterName;
        this.secondRegisterName = secondRegisterName;
    }

    @Override
    public void execute(Registers registers, RandomAccessMemory randomAccessMemory, Stack stack, Display display) {
        var register = registers.generalPurpose(firstRegisterName);
        register.set(register.get().sum(registers.generalPurpose(secondRegisterName).get()));
    }
}
