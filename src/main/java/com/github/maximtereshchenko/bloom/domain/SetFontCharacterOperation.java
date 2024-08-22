package com.github.maximtereshchenko.bloom.domain;

/**
 * The index register I is set to the address of the hexadecimal character in VX.
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
