package com.github.maximtereshchenko.bloom;

import com.github.maximtereshchenko.bloom.api.port.Randomness;

final class FakeRandomness implements Randomness {

    @Override
    public int randomNumber(int fromInclusive, int toInclusive) {
        return 0xFF;
    }
}
