package com.github.maximtereshchenko.bloom.application;

import com.github.maximtereshchenko.bloom.api.port.Randomness;
import java.util.Random;

final class TrueRandomness implements Randomness {

    private final Random random = new Random();

    @Override
    public int randomNumber(int fromInclusive, int toInclusive) {
        return random.nextInt(fromInclusive, toInclusive + 1);
    }
}
