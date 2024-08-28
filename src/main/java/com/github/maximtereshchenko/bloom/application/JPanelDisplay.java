package com.github.maximtereshchenko.bloom.application;

import com.github.maximtereshchenko.bloom.api.DisplayMaskUseCase;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

final class JPanelDisplay extends JPanel {

    private final DisplayMaskUseCase useCase;

    private JPanelDisplay(DisplayMaskUseCase useCase, Dimension preferredSize) {
        this.useCase = useCase;
        setPreferredSize(preferredSize);
    }

    static JPanelDisplay from(DisplayMaskUseCase useCase) {
        var displayMask = useCase.displayMask();
        return new JPanelDisplay(
            useCase,
            new Dimension(
                displayMask.length > 0 ? displayMask[0].length * 10 : 0,
                displayMask.length * 10
            )
        );
    }

    @Override
    protected void paintComponent(Graphics g) {
        var displayMask = useCase.displayMask();
        var pixelHeight = getHeight() / displayMask.length;
        for (var row = 0; row < displayMask.length; row++) {
            var pixelWidth = getWidth() / displayMask[row].length;
            for (var column = 0; column < displayMask[row].length; column++) {
                g.setColor(displayMask[row][column] ? Color.WHITE : Color.BLACK);
                g.fillRect(column * pixelWidth, row * pixelHeight, pixelWidth, pixelHeight);
            }
        }
    }
}
