package com.github.maximtereshchenko.bloom.domain;

abstract class RegisterMemoryTransferOperation implements Operation {

    private final Registers registers;
    private final RandomAccessMemory randomAccessMemory;
    private final HexadecimalSymbol lastRegisterName;

    RegisterMemoryTransferOperation(
        Registers registers,
        RandomAccessMemory randomAccessMemory,
        HexadecimalSymbol lastRegisterName
    ) {
        this.registers = registers;
        this.randomAccessMemory = randomAccessMemory;
        this.lastRegisterName = lastRegisterName;
    }

    @Override
    public void execute() {
        var index = registers.index();
        var memoryAddress = index.get();
        for (var registerName : HexadecimalSymbol.values()) {
            transfer(randomAccessMemory, memoryAddress, registers.generalPurpose(registerName));
            memoryAddress = memoryAddress.next();
            if (registerName == lastRegisterName) {
                break;
            }
        }
        index.set(memoryAddress);
    }

    abstract void transfer(
        RandomAccessMemory randomAccessMemory,
        MemoryAddress memoryAddress,
        Register<UnsignedByte> register
    );
}
