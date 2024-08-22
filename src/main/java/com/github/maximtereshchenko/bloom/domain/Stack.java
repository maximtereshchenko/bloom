package com.github.maximtereshchenko.bloom.domain;

import java.util.Deque;
import java.util.LinkedList;

final class Stack {

    private final Deque<MemoryAddress> deque = new LinkedList<>();

    void push(MemoryAddress memoryAddress) {
        deque.addLast(memoryAddress);
    }

    void pop() {
        deque.removeLast();
    }

    MemoryAddress pick() {
        return deque.getLast();
    }
}
