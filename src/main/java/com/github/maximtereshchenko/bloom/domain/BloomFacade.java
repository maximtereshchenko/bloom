package com.github.maximtereshchenko.bloom.domain;

import com.github.maximtereshchenko.bloom.api.BloomModule;
import java.util.Optional;
import java.util.Set;

public final class BloomFacade implements BloomModule {

    private final RandomAccessMemory randomAccessMemory;
    private final Registers registers;
    private final Stack stack;
    private final Display display;
    private final Set<OperationFactory> operationFactories;

    public BloomFacade(byte[] program) {
        this.randomAccessMemory = RandomAccessMemory.withProgram(program);
        this.registers = new Registers();
        this.stack = new Stack();
        this.display = new Display();
        this.operationFactories = Set.of(
            new SetFontCharacterOperationFactory(),
            new DisplayOperationFactory(),
            new ClearDisplayOperationFactory(),
            new JumpOperationFactory(),
            new SetIndexRegisterValueOperationFactory(),
            new CallSubroutineOperationFactory(),
            new ReturnFromSubroutineOperationFactory(),
            new AddToRegisterValueOperationFactory(),
            new BinaryAndRegisterValuesOperationFactory(),
            new BinaryOrRegisterValuesOperationFactory(),
            new BinaryXorRegisterValuesOperationFactory(),
            new CopyRegisterValueOperationFactory(),
            new SetRegisterValueOperationFactory(),
            new ShiftRightOperationFactory(),
            new SubtractRegisterValuesOperationFactory(),
            new SumRegisterValuesOperationFactory(),
            new SkipIfRegisterValuesEqualOperationFactory(),
            new SkipIfRegisterValuesNotEqualOperationFactory(),
            new SkipIfSingleRegisterValueEqualOperationFactory(),
            new SkipIfSingleRegisterValueNotEqualOperationFactory()
        );
    }

    @Override
    public void executeNextInstruction() {
        var operationCode = operationCode();
        operationFactories.stream()
            .map(operationFactory -> operationFactory.operation(operationCode))
            .flatMap(Optional::stream)
            .findAny()
            .orElseThrow(() -> new IllegalArgumentException(operationCode.toString()))
            .execute(registers, randomAccessMemory, stack, display);
    }

    @Override
    public boolean[][] displayMask() {
        return display.mask();
    }

    private OperationCode operationCode() {
        var first = registers.programCounter().get();
        var second = first.next();
        var operationCode = new OperationCode(randomAccessMemory.value(first), randomAccessMemory.value(second));
        registers.programCounter().set(second.next());
        return operationCode;
    }
}
