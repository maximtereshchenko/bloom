package com.github.maximtereshchenko.bloom.domain;

final class GeneralPurpose implements Register<Byte> {

    private Byte value = new Byte();

    @Override
    public Byte value() {
        return value;
    }

    @Override
    public void set(Byte value) {
        this.value = value;
    }
}
