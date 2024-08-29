package com.github.maximtereshchenko.bloom.application;

import com.github.maximtereshchenko.bloom.api.port.Keypad;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

final class KeyListenerKeypad extends KeyAdapter implements Keypad {

    private final Map<Character, Character> mapping = mapping();
    private final Set<Character> pressed = new CopyOnWriteArraySet<>();
    private final BlockingQueue<Character> queue = new SynchronousQueue<>();

    @Override
    public boolean isPressed(char key) {
        return pressed.contains(key);
    }

    @Override
    public char nextPressedKey() {
        try {
            return queue.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        onKeyEvent(e, pressed::add);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        onKeyEvent(e, this::onKeyReleased);
    }

    private void onKeyEvent(KeyEvent keyEvent, Consumer<Character> consumer) {
        var mapped = mapping.get(keyEvent.getKeyChar());
        if (mapped == null) {
            return;
        }
        consumer.accept(mapped);
    }

    private void onKeyReleased(Character mapped) {
        try {
            pressed.remove(mapped);
            queue.offer(mapped, 0, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private Map<Character, Character> mapping() {
        var keys = new HashMap<Character, Character>();
        keys.put('1', '1');
        keys.put('2', '2');
        keys.put('3', '3');
        keys.put('4', 'C');
        keys.put('q', '4');
        keys.put('w', '5');
        keys.put('e', '6');
        keys.put('r', 'D');
        keys.put('a', '7');
        keys.put('s', '8');
        keys.put('d', '9');
        keys.put('f', 'E');
        keys.put('z', 'A');
        keys.put('x', '0');
        keys.put('c', 'B');
        keys.put('v', 'F');
        return keys;
    }
}
