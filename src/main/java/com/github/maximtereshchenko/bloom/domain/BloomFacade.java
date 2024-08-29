package com.github.maximtereshchenko.bloom.domain;

import com.github.maximtereshchenko.bloom.api.BloomModule;
import com.github.maximtereshchenko.bloom.api.port.Display;
import com.github.maximtereshchenko.bloom.api.port.Keypad;
import com.github.maximtereshchenko.bloom.api.port.Randomness;
import com.github.maximtereshchenko.bloom.api.port.Sound;

import java.util.Set;

public final class BloomFacade implements BloomModule {

    private final Registers registers;
    private final RandomAccessMemory randomAccessMemory;
    private final DelayTimer delayTimer;
    private final SoundTimer soundTimer;
    private final Set<OperationFactory> operationFactories;

    public BloomFacade(
        byte[] program,
        Display display,
        Keypad keypad,
        Sound sound,
        Randomness randomness
    ) {
        this.registers = new Registers();
        this.randomAccessMemory = RandomAccessMemory.withProgram(program);
        this.delayTimer = new DelayTimer();
        this.soundTimer = new SoundTimer(sound);
        var stack = new Stack();
        var stagingDisplay = new StagingDisplay(display);
        this.operationFactories = Set.of(
            new SetFontCharacterOperationFactory(registers, randomAccessMemory),
            new DisplayOperationFactory(registers, randomAccessMemory, stagingDisplay),
            new ClearDisplayOperationFactory(stagingDisplay),
            new JumpOperationFactory(registers),
            new JumpWithOffsetOperationFactory(registers),
            new SetIndexOperationFactory(registers),
            new CallSubroutineOperationFactory(registers, stack),
            new ReturnFromSubroutineOperationFactory(registers, stack),
            new AddOperationFactory(registers),
            new BinaryAndOperationFactory(registers),
            new BinaryOrOperationFactory(registers),
            new BinaryXorOperationFactory(registers),
            new CopyOperationFactory(registers),
            new SetOperationFactory(registers),
            new ShiftRightOperationFactory(registers),
            new ShiftLeftOperationFactory(registers),
            new SubtractOperationFactory(registers),
            new NegativeSubtractOperationFactory(registers),
            new SumOperationFactory(registers),
            new SkipIfRegisterValuesEqualOperationFactory(registers),
            new SkipIfRegisterValuesNotEqualOperationFactory(registers),
            new SkipIfSingleRegisterValueEqualOperationFactory(registers),
            new SkipIfSingleRegisterValueNotEqualOperationFactory(registers),
            new SkipIfKeyPressedOperationFactory(registers, keypad),
            new SkipIfKeyNotPressedOperationFactory(registers, keypad),
            new ConvertToBinaryCodedDecimalOperationFactory(registers, randomAccessMemory),
            new StoreOperationFactory(registers, randomAccessMemory),
            new LoadOperationFactory(registers, randomAccessMemory),
            new AddToIndexOperationFactory(registers),
            new GetKeyOperationFactory(registers, keypad),
            new SetDelayTimerOperationFactory(registers, delayTimer),
            new ReadDelayTimerOperationFactory(registers, delayTimer),
            new SetSoundTimerOperationFactory(registers, soundTimer),
            new RandomOperationFactory(registers, randomness)
        );
    }

    @Override
    public void executeNextOperation() {
        var operationCode = operationCode();
        operationFactories.stream()
            .filter(operationFactory -> operationFactory.supports(operationCode))
            .map(operationFactory -> operationFactory.supportedOperation(operationCode))
            .findAny()
            .orElseThrow(() -> new IllegalArgumentException(operationCode.toString()))
            .execute();
    }

    @Override
    public void decrementSoundTimer() {
        soundTimer.decrement();
    }

    @Override
    public void decrementDelayTimer() {
        delayTimer.decrement();
    }

    private OperationCode operationCode() {
        var first = registers.programCounter().get();
        var second = first.next();
        var operationCode = new OperationCode(
            randomAccessMemory.get(first),
            randomAccessMemory.get(second)
        );
        registers.programCounter().set(second.next());
        return operationCode;
    }
}
