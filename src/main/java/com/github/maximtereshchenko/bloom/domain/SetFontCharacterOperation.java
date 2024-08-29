package com.github.maximtereshchenko.bloom.domain;

/**
 * Fx29 - LD F, Vx. Set I = location of sprite for digit Vx. The value of I is set to the
 * location for the hexadecimal sprite corresponding to the value of Vx.
 */
final class SetFontCharacterOperation implements Operation {

    private final Registers registers;
    private final RandomAccessMemory randomAccessMemory;
    private final HexadecimalSymbol registerName;

    SetFontCharacterOperation(
        Registers registers,
        RandomAccessMemory randomAccessMemory,
        HexadecimalSymbol registerName
    ) {
        this.registers = registers;
        this.randomAccessMemory = randomAccessMemory;
        this.registerName = registerName;
    }

    @Override
    public void execute() {
        registers.index()
            .set(
                randomAccessMemory.fontCharacterAddress(
                    registers.generalPurpose(registerName)
                        .get()
                        .hexadecimal()
                        .last()
                )
            );
    }
}
