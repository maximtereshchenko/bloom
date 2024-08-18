package com.github.maximtereshchenko.bloom.domain;

import com.github.maximtereshchenko.bloom.api.Display;

interface Operation {

    void execute(Registers registers, RandomAccessMemory randomAccessMemory, Display display);
}
