package com.hertzog.analyzer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CommonColorsViewTests {
    private int GRANULARITY = 10;
    private int NUM_COLORS_TO_FIND = 1;
    private String IMAGE_HEX = "#000000";
    private String EXCEPTION_MESSAGE = "Percentage map must have at least one entry!";

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
        returnedMap.put(IMAGE_HEX, 1.0);
        when(converter.getHexPercentagesMap(image, NUM_COLORS_TO_FIND, GRANULARITY))
                .thenReturn(returnedMap);

        sut.drawCommonImageColorsAsPieChart(image, NUM_COLORS_TO_FIND, GRANULARITY);

        verify(converter).getHexPercentagesMap(image, NUM_COLORS_TO_FIND, GRANULARITY);
    }

    @Test
    public void whenDrawCommonImageColorsFromPercentageMap_givenGoodMap_thenNoExceptionsThrown() {
        Map<String, Double> percentages = new HashMap<>();
        percentages.put(IMAGE_HEX, 1.0);

        sut.drawCommonImageColorsFromPercentageMap(percentages);
    }

    @Test
    public void whenDrawCommonImageColorsFromPercentageMap_givenEmptyMap_thenThrowsIllegalArgumentException() {
        Exception e = assertThrows(IllegalArgumentException.class,
                () -> sut.drawCommonImageColorsFromPercentageMap(new HashMap<>()));

        assertEquals(e.getMessage(), EXCEPTION_MESSAGE);
    }
}
