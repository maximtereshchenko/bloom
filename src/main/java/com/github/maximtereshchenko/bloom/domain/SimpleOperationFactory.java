package com.github.maximtereshchenko.bloom.domain;

import java.util.Optional;

abstract class SimpleOperationFactory implements OperationFactory {

    @Override
    public Optional<Operation> operation(OperationCode operationCode) {
        if (supports(operationCode)) {
            return Optional.of(supportedOperation(operationCode));
        }
        return Optional.empty();
    }

    abstract boolean supports(OperationCode operationCode);

    abstract Operation supportedOperation(OperationCode operationCode);
}
