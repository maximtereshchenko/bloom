package com.github.maximtereshchenko.bloom.domain;

/**
 * Fx55 - LD [I], Vx. Store registers V0 through Vx in memory starting at location I. The
 * interpreter copies the values of registers V0 through Vx into memory, starting at the address
 * in I. Index is incremented along the way.
 */
final class StoreOperation extends RegisterMemoryTransferOperation {

    StoreOperation(
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
        randomAccessMemory.set(memoryAddress, register.get());
    }
}
