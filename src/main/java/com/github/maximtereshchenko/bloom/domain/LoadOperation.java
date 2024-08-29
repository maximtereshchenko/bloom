package com.github.maximtereshchenko.bloom.domain;

/**
 * Fx65 - LD Vx, [I]. Read registers V0 through Vx from memory starting at location I. The
 * interpreter reads values from memory starting at location I into registers V0 through Vx.
 * Index is incremented along the way.
 */
final class LoadOperation extends RegisterMemoryTransferOperation {

    LoadOperation(
        Registers registers,
        RandomAccessMemory randomAccessMemory,
        HexadecimalSymbol lastRegisterName
    ) {
        super(registers, randomAccessMemory, lastRegisterName);
    }

    @Override
    void transfer(
        RandomAccessMemory randomAccessMemory,
        MemoryAddress memoryAddress,
        Register<UnsignedByte> register
    ) {
        register.set(randomAccessMemory.get(memoryAddress));
    }
}
