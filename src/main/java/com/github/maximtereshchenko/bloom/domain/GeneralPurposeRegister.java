package com.github.maximtereshchenko.bloom.domain;

final class GeneralPurposeRegister implements Register<Byte> {

    private Byte value = new Byte();

    @Override
    public Byte get() {
        return value;
    }

    @Override
    public void set(Byte value) {
        this.value = value;
    }
}
