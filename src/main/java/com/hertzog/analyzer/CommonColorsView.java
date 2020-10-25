package com.hertzog.analyzer;

import org.springframework.lang.NonNull;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CommonColorsView {
    private ImageToHexValuesConverter converter;

    public CommonColorsView(ImageToHexValuesConverter converter) {
        this.converter = converter;
    }

    public void drawCommonImageColorsAsPieChart(@NonNull BufferedImage image,
                                                       int numColorsToInclude,
                                                       int granularity) {
        Map<String, Double> hexPercentagesMap =
                converter.getHexPercentagesMap(image, numColorsToInclude, granularity);

        drawCommonImageColorsFromPercentageMap(hexPercentagesMap);
    }

    public void drawCommonImageColorsFromPercentageMap(Map<String, Double> percentageMap) {
        List<String> hexVals = new ArrayList<>();
        List<Double> percentages = new ArrayList<>();
        for (Map.Entry<String, Double> e : percentageMap.entrySet()) {
            hexVals.add(e.getKey());
            percentages.add(e.getValue());
        }

        drawPieChartInFrame(new PieChart(hexVals, percentages));
    }

    private void drawPieChartInFrame(PieChart pieChart) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(pieChart);
        frame.setSize(new Dimension(500, 500));
        frame.setVisible(true);
    }
}
