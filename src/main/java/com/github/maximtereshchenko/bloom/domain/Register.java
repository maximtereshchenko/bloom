package com.github.maximtereshchenko.bloom.domain;

interface Register<T> {

    T get();

    void set(T value);
}
