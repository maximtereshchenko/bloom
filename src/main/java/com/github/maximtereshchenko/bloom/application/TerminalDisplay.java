package com.github.maximtereshchenko.bloom.application;

import com.github.maximtereshchenko.bloom.api.DisplayMaskUseCase;
import java.io.PrintStream;

final class TerminalDisplay {

    private static final String ERASE_LINE = "\033[2k";
    private static final String BLACK_BACKGROUND = "\033[48;5;0m";
    private static final String WHITE_BACKGROUND = "\033[48;5;7m";

    private final PrintStream printStream;
    private final DisplayMaskUseCase useCase;
    private boolean isOutputEmpty = true;

    TerminalDisplay(PrintStream printStream, DisplayMaskUseCase useCase) {
        this.printStream = printStream;
        this.useCase = useCase;
    }

    void draw() {
        var displayMask = useCase.displayMask();
        if (!isOutputEmpty) {
            erase(displayMask.length);
        }
        print(displayMask);
        isOutputEmpty = false;
    }

    private void erase(int height) {
        printStream.print(ERASE_LINE.repeat(height + 1));
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
