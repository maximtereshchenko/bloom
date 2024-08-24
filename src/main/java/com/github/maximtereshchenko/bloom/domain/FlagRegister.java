package com.github.maximtereshchenko.bloom.domain;

final class FlagRegister implements Register<UnsignedByte> {

    private UnsignedByte value = new UnsignedByte();

    @Override
    public UnsignedByte get() {
        return value;
    }

    @Override
    public void set(UnsignedByte value) {
        this.value = value;
    }

    void set(boolean value) {
        set(UnsignedByte.from(value ? 1 : 0));
    }
}
