package com.github.maximtereshchenko.bloom.domain;

/**
 * Fx55 - LD [I], Vx. Store registers V0 through Vx in memory starting at location I. The interpreter copies the values
 * of registers V0 through Vx into memory, starting at the address in I.
 */
final class StoreOperation implements Operation {

    private final Registers registers;
    private final RandomAccessMemory randomAccessMemory;
    private final HexadecimalSymbol lastRegisterName;

    StoreOperation(Registers registers, RandomAccessMemory randomAccessMemory, HexadecimalSymbol lastRegisterName) {
        this.registers = registers;
        this.randomAccessMemory = randomAccessMemory;
        this.lastRegisterName = lastRegisterName;
    }

    @Override
    public void execute() {
        var memoryAddress = registers.index().get();
        for (var registerName : HexadecimalSymbol.values()) {
            randomAccessMemory.set(memoryAddress, registers.generalPurpose(registerName).get());
            if (registerName == lastRegisterName) {
                break;
            }
            memoryAddress = memoryAddress.next();
        }
    }
}
