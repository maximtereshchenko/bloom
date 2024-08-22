package com.github.maximtereshchenko.bloom.domain;

/**
 * VX is set to the bitwise/binary logical disjunction (OR) of VX and VY. VY is not affected.
 */
final class BinaryOrOperation implements Operation {

    private final HexadecimalSymbol firstRegisterName;
    private final HexadecimalSymbol secondRegisterName;

    BinaryOrOperation(HexadecimalSymbol firstRegisterName, HexadecimalSymbol secondRegisterName) {
        this.firstRegisterName = firstRegisterName;
        this.secondRegisterName = secondRegisterName;
    }

    @Override
    public void execute(Registers registers, RandomAccessMemory randomAccessMemory, Stack stack, Display display) {
        var register = registers.generalPurpose(firstRegisterName);
        register.set(register.get().or(registers.generalPurpose(secondRegisterName).get()));
    }
}
