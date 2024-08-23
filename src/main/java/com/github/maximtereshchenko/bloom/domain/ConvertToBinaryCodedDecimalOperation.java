package com.github.maximtereshchenko.bloom.domain;

/**
 * Fx33 - LD B, Vx. Store BCD representation of Vx in memory locations I, I+1, and I+2. The interpreter takes the
 * decimal value of Vx, and places the hundreds digit in memory at location in I, the tens digit at location I+1, and
 * the ones digit at location I+2.
 */
final class ConvertToBinaryCodedDecimalOperation implements Operation {

    private final HexadecimalSymbol registerName;

    ConvertToBinaryCodedDecimalOperation(HexadecimalSymbol registerName) {
        this.registerName = registerName;
    }

    @Override
    public void execute(Registers registers, RandomAccessMemory randomAccessMemory, Stack stack, Display display) {
        var memoryAddress = registers.index().get();
        for (var component : registers.generalPurpose(registerName).get().binaryCodedDecimal()) {
            randomAccessMemory.set(memoryAddress, component);
            memoryAddress = memoryAddress.next();
        }
    }
}
