package com.github.maximtereshchenko.bloom.domain;

import com.github.maximtereshchenko.bloom.api.port.Randomness;

/**
 * Cxkk - RND Vx, byte. Set Vx = random byte AND kk. The interpreter generates a random number
 * from 0 to 255, which is then ANDed with the value kk. The results are stored in Vx.
 */
final class RandomOperation implements Operation {

    private final Registers registers;
    private final Randomness randomness;
    private final HexadecimalSymbol registerName;
    private final UnsignedByte mask;

    RandomOperation(
        Registers registers,
        Randomness randomness,
        HexadecimalSymbol registerName,
        UnsignedByte mask
    ) {
        this.registers = registers;
        this.randomness = randomness;
        this.registerName = registerName;
        this.mask = mask;
    }

    @Override
    public void execute() {
        registers.generalPurpose(registerName).set(UnsignedByte.random(randomness).and(mask));
    }
}
