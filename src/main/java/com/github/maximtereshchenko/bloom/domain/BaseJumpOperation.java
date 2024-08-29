package com.github.maximtereshchenko.bloom.domain;

abstract class BaseJumpOperation implements Operation {

    private final Registers registers;
    private final MemoryAddress memoryAddress;

    BaseJumpOperation(Registers registers, MemoryAddress memoryAddress) {
        this.registers = registers;
        this.memoryAddress = memoryAddress;
    }

    @Override
    public void execute() {
        registers.programCounter()
            .set(memoryAddress.withOffset(offset()));
    }

    abstract UnsignedByte offset();
}
