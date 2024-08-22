package com.github.maximtereshchenko.bloom.domain;

/**
 * Skip one instruction if the value in VX is equal to NN.
 */
final class SkipIfRegisterValueEqualsOperation implements Operation {

    private final HexadecimalSymbol registerName;
    private final Byte value;

    SkipIfRegisterValueEqualsOperation(HexadecimalSymbol registerName, Byte value) {
        this.registerName = registerName;
        this.value = value;
    }

    @Override
    public void execute(Registers registers, RandomAccessMemory randomAccessMemory, Stack stack, Display display) {
        if (registers.generalPurpose(registerName).value().equals(value)) {
            var programCounter = registers.programCounter();
            programCounter.set(programCounter.value().next().next());
        }
    }
}
