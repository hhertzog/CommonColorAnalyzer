package com.hertzog.analyzer;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PieChart extends JPanel {
    private List<String> hexColors;
    private List<Double> percentages;

    public PieChart(List<String> hexColors, List<Double> percentages) {
        this.hexColors = hexColors;
        this.percentages = percentages;
    }

    @Override
    protected void paintComponent(Graphics g) {
        int width = getWidth();
        int height = getHeight();
        Graphics2D g2d = (Graphics2D) g;

        int sliceStart = 0;
        for (int i = 0; i < hexColors.size(); i++) {
            g2d.setColor(getColorFromHex(hexColors.get(i)));

            Double angle = percentages.get(i) * 360;
            g2d.fillArc(0, 0, width, height, sliceStart, angle.intValue());

            sliceStart = sliceStart + angle.intValue();
        }
    }

    private Color getColorFromHex(String hexCode) {
        return Color.decode(hexCode);
    }
}
