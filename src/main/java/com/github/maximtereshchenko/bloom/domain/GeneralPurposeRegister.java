package com.github.maximtereshchenko.bloom.domain;

final class GeneralPurposeRegister implements Register<UnsignedByte> {

    private UnsignedByte value = UnsignedByte.ZERO;

    @Override
    public UnsignedByte get() {
        return value;
    }

    @Override
    public void set(UnsignedByte value) {
        this.value = value;
    }
}
