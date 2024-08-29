package com.github.maximtereshchenko.bloom.domain;

import com.github.maximtereshchenko.bloom.api.port.Randomness;

final class RandomOperationFactory implements OperationFactory {

    private final Registers registers;
    private final Randomness randomness;

    RandomOperationFactory(Registers registers, Randomness randomness) {
        this.registers = registers;
        this.randomness = randomness;
    }

    @Override
    public boolean supports(OperationCode operationCode) {
        return operationCode.firstNibble() == HexadecimalSymbol.C;
    }

    @Override
    public Operation supportedOperation(OperationCode operationCode) {
        return new RandomOperation(
            registers,
            randomness,
            operationCode.middleLeftNibble(),
            new Hexadecimal(operationCode.middleRightNibble(), operationCode.lastNibble()).unsignedByte()
        );
    }
}
