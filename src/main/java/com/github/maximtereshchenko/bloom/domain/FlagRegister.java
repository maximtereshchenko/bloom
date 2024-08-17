package com.github.maximtereshchenko.bloom.domain;

final class FlagRegister implements Register<Byte> {

    private Byte value = new Byte();

    @Override
    public Byte value() {
        return value;
    }

    @Override
    public void set(Byte value) {
        this.value = value;
    }

    void disable() {
        set(Byte.from(0));
    }

    void enable() {
        set(Byte.from(1));
    }
}
