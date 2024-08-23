package com.github.maximtereshchenko.bloom.domain;

import java.util.Optional;

final class SkipConditionallyOperationFactory implements OperationFactory {

    @Override
    public Optional<Operation> operation(OperationCode operationCode) {
        return switch (operationCode.firstNibble()) {
            case THREE -> Optional.of(
                new SkipIfSingleRegisterValueEqualOperation(
                    operationCode.middleLeftNibble(),
                    new Hexadecimal(operationCode.middleRightNibble(), operationCode.lastNibble()).unsignedByte()
                )
            );
            case FOUR -> Optional.of(
                new SkipIfSingleRegisterValueNotEqualOperation(
                    operationCode.middleLeftNibble(),
                    new Hexadecimal(operationCode.middleRightNibble(), operationCode.lastNibble()).unsignedByte()
                )
            );
            default -> skipIfRegisterValues(operationCode);
        };
    }

    private Optional<Operation> skipIfRegisterValues(OperationCode operationCode) {
        if (operationCode.lastNibble() != HexadecimalSymbol.ZERO) {
            return Optional.empty();
        }
        return switch (operationCode.firstNibble()) {
            case FIVE -> Optional.of(
                new SkipIfRegisterValuesEqualOperation(
                    operationCode.middleLeftNibble(),
                    operationCode.middleRightNibble()
                )
            );
            case NINE -> Optional.of(
                new SkipIfRegisterValuesNotEqualOperation(
                    operationCode.middleLeftNibble(),
                    operationCode.middleRightNibble()
                )
            );
            default -> Optional.empty();
        };
    }
}
