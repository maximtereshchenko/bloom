package com.github.maximtereshchenko.bloom.domain;

final class RandomAccessMemory {

    private static final MemoryAddress FONT_MEMORY_ADDRESS = MemoryAddress.from(0x050);
    private final UnsignedByte[] bytes;

    private RandomAccessMemory(UnsignedByte[] bytes) {
        this.bytes = bytes;
    }

    static RandomAccessMemory withProgram(byte[] program) {
        var array = new UnsignedByte[4096];
        var fontBits = new Font().bytes();
        System.arraycopy(fontBits, 0, array, FONT_MEMORY_ADDRESS.primitive(), fontBits.length);
        for (int i = 0; i < program.length; i++) {
            array[ProgramCounter.INITIAL.primitive() + i] = UnsignedByte.from(program[i]);
        }
        return new RandomAccessMemory(array);
    }

    MemoryAddress fontCharacterAddress(HexadecimalSymbol character) {
        return FONT_MEMORY_ADDRESS.withOffset(character.ordinal() * 5);
    }

    UnsignedByte get(MemoryAddress memoryAddress) {
        var value = bytes[memoryAddress.primitive()];
        if (value == null) {
            return UnsignedByte.ZERO;
        }
        return value;
    }

    void set(MemoryAddress memoryAddress, UnsignedByte value) {
        bytes[memoryAddress.primitive()] = value;
    }
}
