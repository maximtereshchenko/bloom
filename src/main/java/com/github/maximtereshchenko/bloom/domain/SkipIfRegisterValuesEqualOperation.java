package com.github.maximtereshchenko.bloom.domain;

/**
 * Skip one instruction if the values in VX and VY are equal
 */
final class SkipIfRegisterValuesEqualOperation implements Operation {

    private final HexadecimalSymbol firstRegisterName;
    private final HexadecimalSymbol secondRegisterName;

    SkipIfRegisterValuesEqualOperation(HexadecimalSymbol firstRegisterName, HexadecimalSymbol secondRegisterName) {
        this.firstRegisterName = firstRegisterName;
        this.secondRegisterName = secondRegisterName;
    }

    @Override
    public void execute(Registers registers, RandomAccessMemory randomAccessMemory, Stack stack, Display display) {
        if (value(registers, firstRegisterName).equals(value(registers, secondRegisterName))) {
            var programCounter = registers.programCounter();
            programCounter.set(programCounter.get().next().next());
        }
    }

    private Byte value(Registers registers, HexadecimalSymbol registerName) {
        return registers.generalPurpose(registerName).get();
    }
}
