package com.github.maximtereshchenko.bloom.domain;

/**
 * Fx1E - ADD I, Vx. Set I = I + Vx. The values of I and Vx are added, and the results are stored in I.
 */
final class AddToIndexOperation implements Operation {

    private final HexadecimalSymbol registerName;

    AddToIndexOperation(HexadecimalSymbol registerName) {
        this.registerName = registerName;
    }

    @Override
    public void execute(Registers registers, RandomAccessMemory randomAccessMemory, Stack stack, Display display) {
        var index = registers.index();
        index.set(index.get().withOffset(registers.generalPurpose(registerName).get()));
    }
}
