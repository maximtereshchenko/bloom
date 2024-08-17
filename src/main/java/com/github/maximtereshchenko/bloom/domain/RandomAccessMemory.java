package com.github.maximtereshchenko.bloom.domain;

final class RandomAccessMemory {

    private static final MemoryAddress FONT_MEMORY_ADDRESS = MemoryAddress.from(0x050);
    private final Byte[] bytes;

    private RandomAccessMemory(Byte[] bytes) {
        this.bytes = bytes;
    }

    static RandomAccessMemory withProgram(byte[] program) {
        var array = new Byte[4096];
        var fontBits = new Font().bytes();
        System.arraycopy(fontBits, 0, array, FONT_MEMORY_ADDRESS.value(), fontBits.length);
        for (int i = 0; i < program.length; i++) {
            array[ProgramCounter.INITIAL.value() + i] = new Byte(program[i]);
        }
        return new RandomAccessMemory(array);
    }

    MemoryAddress fontCharacterAddress(HexadecimalSymbol character) {
        return FONT_MEMORY_ADDRESS.withOffset(character.ordinal() * 5);
    }

    Byte value(MemoryAddress memoryAddress) {
        return bytes[memoryAddress.value()];
    }
}
