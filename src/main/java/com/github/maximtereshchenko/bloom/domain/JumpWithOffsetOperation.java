package com.github.maximtereshchenko.bloom.domain;

/**
 * Bnnn - JP V0, addr. Jump to location nnn + V0. The program counter is set to nnn plus the value of V0.
 */
final class JumpWithOffsetOperation extends BaseJumpOperation {

    private final Registers registers;

    JumpWithOffsetOperation(Registers registers, MemoryAddress memoryAddress) {
        super(registers, memoryAddress);
        this.registers = registers;
    }

    @Override
    UnsignedByte offset() {
        return registers.generalPurpose(HexadecimalSymbol.ZERO).get();
    }
}
