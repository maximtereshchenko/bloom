package com.github.maximtereshchenko.bloom.domain;

abstract class Timer {

    private UnsignedByte value = UnsignedByte.ZERO;

    synchronized void set(UnsignedByte value) {
        this.value = value;
        if (this.value.compareTo(UnsignedByte.ZERO) > 0) {
            onCountDownStart(this.value);
        }
    }

    synchronized UnsignedByte get() {
        return value;
    }

    synchronized void decrement() {
        if (value.equals(UnsignedByte.ZERO)) {
            return;
        }
        value = value.difference(UnsignedByte.from(1));
        if (value.equals(UnsignedByte.ZERO)) {
            onCountDownEnd();
        }
    }

    abstract void onCountDownStart(UnsignedByte start);

    abstract void onCountDownEnd();
}
