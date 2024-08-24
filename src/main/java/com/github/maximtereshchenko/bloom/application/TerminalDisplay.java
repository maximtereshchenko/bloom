package com.github.maximtereshchenko.bloom.application;

import com.github.maximtereshchenko.bloom.api.DisplayMaskUseCase;
import java.io.PrintStream;
import java.util.Arrays;

final class TerminalDisplay implements AutoCloseable {

    private static final String HIDE_CURSOR = "\033[?25l";
    private static final String SHOW_CURSOR = "\033[?25h";
    private static final String MOVE_CURSOR_34_LINES_UP = "\033[34F";
    private static final String BLACK_BACKGROUND = "\033[48;5;0m";
    private static final String WHITE_BACKGROUND = "\033[48;5;7m";

    private final PrintStream printStream;
    private final DisplayMaskUseCase useCase;
    private boolean[][] previousDisplayMask = new boolean[0][0];

    TerminalDisplay(PrintStream printStream, DisplayMaskUseCase useCase) {
        this.printStream = printStream;
        this.useCase = useCase;
    }

    @Override
    public void close() {
        printStream.print(SHOW_CURSOR);
    }

    void draw() {
        var displayMask = useCase.displayMask();
        if (Arrays.deepEquals(displayMask, previousDisplayMask)) {
            return;
        }
        if (previousDisplayMask.length == 0) {
            printStream.print(HIDE_CURSOR);
        } else {
            printStream.print(MOVE_CURSOR_34_LINES_UP);
        }
        print(displayMask);
        previousDisplayMask = displayMask;
    }

    private void print(boolean[][] displayMask) {
        boolean isBackgroundBlack = true;
        printStream.print(BLACK_BACKGROUND);
        for (int i = 0; i < displayMask.length; i++) {
            var row = displayMask[i];
            if (i == 0) {
                printBorder(row.length);
            }
            printStream.print('|');
            for (var isPixelEnabled : row) {
                if (isPixelEnabled && isBackgroundBlack) {
                    printStream.print(WHITE_BACKGROUND);
                    isBackgroundBlack = false;
                }
                if (!isPixelEnabled && !isBackgroundBlack) {
                    printStream.print(BLACK_BACKGROUND);
                    isBackgroundBlack = true;
                }
                printStream.print("  ");
            }
            if (!isBackgroundBlack) {
                printStream.print(BLACK_BACKGROUND);
                isBackgroundBlack = true;
            }
            printStream.print('|');
            printStream.print(System.lineSeparator());
            if (i == displayMask.length - 1) {
                printBorder(row.length);
            }
        }
    }

    private void printBorder(int length) {
        printStream.print(' ');
        printStream.print("-".repeat(length * 2));
        printStream.print(' ');
        printStream.print(System.lineSeparator());
    }
}
