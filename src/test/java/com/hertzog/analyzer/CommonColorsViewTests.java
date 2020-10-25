package com.hertzog.analyzer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CommonColorsViewTests {
    private int GRANULARITY = 10;
    private int NUM_COLORS_TO_FIND = 1;

    @Mock
    private BufferedImage image;

    @Mock
    private ImageToHexValuesConverter converter;

    @InjectMocks
    private CommonColorsView sut;

    @BeforeEach
    public void before() {
        System.setProperty("java.awt.headless", "false");
    }

    @Test
    public void whenDrawCommonImageColorsAsPieChart_givenGoodParameters_thenCallsConverter() {
        Map<String, Double> returnedMap = new HashMap<>();
        when(converter.getHexPercentagesMap(image, NUM_COLORS_TO_FIND, GRANULARITY))
                .thenReturn(returnedMap);

        sut.drawCommonImageColorsAsPieChart(image, NUM_COLORS_TO_FIND, GRANULARITY);

        verify(converter).getHexPercentagesMap(image, NUM_COLORS_TO_FIND, GRANULARITY);
    }
}
