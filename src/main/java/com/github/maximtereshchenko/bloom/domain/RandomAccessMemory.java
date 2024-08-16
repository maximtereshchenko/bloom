package com.github.maximtereshchenko.bloom.domain;

final class RandomAccessMemory {

    private static final MemoryAddress FONT_MEMORY_ADDRESS = MemoryAddress.from(0x050);
    private final byte[] bytes;

    private RandomAccessMemory(byte[] bytes) {
        this.bytes = bytes;
    }

    static RandomAccessMemory withProgram(byte[] program) {
        var bytes = new byte[4096];
        new Font().load(bytes, FONT_MEMORY_ADDRESS);
        System.arraycopy(program, 0, bytes, ProgramCounter.INITIAL.value(), program.length);
        return new RandomAccessMemory(bytes);
    }

    MemoryAddress fontCharacterAddress(HexadecimalSymbol character) {
        return FONT_MEMORY_ADDRESS.withOffset(character.ordinal() * 5);
    }

    Bits value(MemoryAddress memoryAddress) {
        return new Bits(bytes[memoryAddress.value()]);
    }
}
