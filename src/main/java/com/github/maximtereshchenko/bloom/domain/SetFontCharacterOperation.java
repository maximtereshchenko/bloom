package com.github.maximtereshchenko.bloom.domain;

final class SetFontCharacterOperation implements Operation {

    private final HexadecimalSymbol character;

    SetFontCharacterOperation(HexadecimalSymbol character) {
        this.character = character;
    }

    @Override
    public void execute(Registers registers, RandomAccessMemory randomAccessMemory) {
        registers.index().set(randomAccessMemory.fontCharacterAddress(character));
    }
}
