package com.github.maximtereshchenko.bloom.domain;

/**
 * Fx29 - LD F, Vx. Set I = location of sprite for digit Vx. The value of I is set to the location for the hexadecimal
 * sprite corresponding to the value of Vx.
 */
final class SetFontCharacterOperation implements Operation {

    private final HexadecimalSymbol registerName;

    SetFontCharacterOperation(HexadecimalSymbol registerName) {
        this.registerName = registerName;
    }

    @Override
    public void execute(Registers registers, RandomAccessMemory randomAccessMemory, Stack stack, Display display) {
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
