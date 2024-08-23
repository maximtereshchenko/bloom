package com.github.maximtereshchenko.bloom.domain;

/**
 * Fx65 - LD Vx, [I]. Read registers V0 through Vx from memory starting at location I. The interpreter reads values from
 * memory starting at location I into registers V0 through Vx.
 */
final class LoadOperation implements Operation {

    private final HexadecimalSymbol lastRegisterName;

    LoadOperation(HexadecimalSymbol lastRegisterName) {
        this.lastRegisterName = lastRegisterName;
    }

    @Override
    public void execute(Registers registers, RandomAccessMemory randomAccessMemory, Stack stack, Display display) {
        var memoryAddress = registers.index().get();
        for (var registerName : HexadecimalSymbol.values()) {
            registers.generalPurpose(registerName).set(randomAccessMemory.get(memoryAddress));
            if (registerName == lastRegisterName) {
                break;
            }
            memoryAddress = memoryAddress.next();
        }
    }
}
