package com.github.maximtereshchenko.bloom.domain;

interface Register<T> {

    T value();

    void set(T value);
}
