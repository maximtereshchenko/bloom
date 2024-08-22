package com.github.maximtereshchenko.bloom.domain;

/**
 * VX is set to the value of VY.
 */
final class CopyRegisterValueOperation implements Operation {

    private final HexadecimalSymbol fromRegisterName;
    private final HexadecimalSymbol toRegisterName;

    CopyRegisterValueOperation(HexadecimalSymbol fromRegisterName, HexadecimalSymbol toRegisterName) {
        this.fromRegisterName = fromRegisterName;
        this.toRegisterName = toRegisterName;
    }

    @Override
    public void execute(Registers registers, RandomAccessMemory randomAccessMemory, Stack stack, Display display) {
        registers.generalPurpose(toRegisterName).set(registers.generalPurpose(fromRegisterName).get());
    }
}
