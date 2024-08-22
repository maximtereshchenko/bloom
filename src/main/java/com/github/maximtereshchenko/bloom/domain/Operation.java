package com.github.maximtereshchenko.bloom.domain;

interface Operation {

    void execute(Registers registers, RandomAccessMemory randomAccessMemory, Stack stack, Display display);
}
