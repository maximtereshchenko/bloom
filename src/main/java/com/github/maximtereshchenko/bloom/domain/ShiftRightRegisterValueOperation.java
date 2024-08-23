package com.github.maximtereshchenko.bloom.domain;

/**
 * 8xy6 - SHR Vx {, Vy}. Set Vx = Vx SHR 1. If the least-significant bit of Vx is 1, then VF is set to 1, otherwise 0.
 * Then Vx is divided by 2.
 */
final class ShiftRightRegisterValueOperation implements Operation {

    private final HexadecimalSymbol registerName;

    ShiftRightRegisterValueOperation(HexadecimalSymbol registerName) {
        this.registerName = registerName;
    }

    @Override
    public void execute(Registers registers, RandomAccessMemory randomAccessMemory, Stack stack, Display display) {
        var register = registers.generalPurpose(registerName);
        var value = register.get();
        var flagRegister = registers.flagRegister();
        flagRegister.disable();
        if (Boolean.TRUE.equals(value.bits().getLast())) {
            flagRegister.enable();
        }
        register.set(value.shiftedRight());
    }
}
