package com.github.maximtereshchenko.bloom.domain;

/**
 * 1nnn - JP addr. Jump to location nnn. The interpreter sets the program counter to nnn.
 */
final class JumpOperation extends BaseJumpOperation {

    JumpOperation(Registers registers, MemoryAddress memoryAddress) {
        super(registers, memoryAddress);
    }

    @Override
    UnsignedByte offset() {
        return UnsignedByte.ZERO;
    }
}
