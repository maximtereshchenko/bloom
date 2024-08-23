package com.github.maximtereshchenko.bloom.domain;

import java.util.Optional;

final class RegisterOperationFactory implements OperationFactory {

    @Override
    public Optional<Operation> operation(OperationCode operationCode) {
        return switch (operationCode.firstNibble()) {
            case SIX -> Optional.of(
                new SetRegisterValueOperation(
                    operationCode.middleLeftNibble(),
                    new Hexadecimal(operationCode.middleRightNibble(), operationCode.lastNibble()).asByte()
                )
            );
            case SEVEN -> Optional.of(
                new AddToRegisterValueOperation(
                    operationCode.middleLeftNibble(),
                    new Hexadecimal(operationCode.middleRightNibble(), operationCode.lastNibble()).asByte()
                )
            );
            case EIGHT -> switch (operationCode.lastNibble()) {
                case ZERO -> Optional.of(
                    new CopyRegisterValueOperation(operationCode.middleRightNibble(), operationCode.middleLeftNibble())
                );
                case ONE -> Optional.of(
                    new BinaryOrRegisterValuesOperation(
                        operationCode.middleLeftNibble(),
                        operationCode.middleRightNibble()
                    )
                );
                case TWO -> Optional.of(
                    new BinaryAndRegisterValuesOperation(
                        operationCode.middleLeftNibble(),
                        operationCode.middleRightNibble()
                    )
                );
                case THREE -> Optional.of(
                    new BinaryXorRegisterValuesOperation(
                        operationCode.middleLeftNibble(),
                        operationCode.middleRightNibble()
                    )
                );
                case FOUR -> Optional.of(
                    new SumRegisterValuesOperation(operationCode.middleLeftNibble(), operationCode.middleRightNibble())
                );
                case FIVE -> Optional.of(
                    new SubtractRegisterValuesOperation(
                        operationCode.middleLeftNibble(),
                        operationCode.middleRightNibble()
                    )
                );
                default -> Optional.empty();
            };
            default -> Optional.empty();
        };
    }
}
