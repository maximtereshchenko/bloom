package com.github.maximtereshchenko.bloom.domain;

import java.util.function.BiConsumer;

final class RegisterOperation implements Operation {

    private final HexadecimalSymbol firstRegisterName;
    private final HexadecimalSymbol secondRegisterName;
    private final BiConsumer<Register<UnsignedByte>, Register<UnsignedByte>> consumer;

    RegisterOperation(
        HexadecimalSymbol firstRegisterName, HexadecimalSymbol secondRegisterName,
        BiConsumer<Register<UnsignedByte>, Register<UnsignedByte>> consumer
    ) {
        this.firstRegisterName = firstRegisterName;
        this.secondRegisterName = secondRegisterName;
        this.consumer = consumer;
    }

    @Override
    public void execute(Registers registers, RandomAccessMemory randomAccessMemory, Stack stack, Display display) {
        consumer.accept(registers.generalPurpose(firstRegisterName), registers.generalPurpose(secondRegisterName));
    }
}
