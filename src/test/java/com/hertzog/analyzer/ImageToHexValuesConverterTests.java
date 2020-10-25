package com.hertzog.analyzer;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.awt.image.BufferedImage;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ImageToHexValuesConverterTests {
    private int GRANULARITY = 10;
    private int NUM_COLORS_TO_FIND = 1;
    private int IMAGE_HEIGHT = 5;
    private int IMAGE_WIDTH = 4;
    private int IMAGE_RGB = 0;
    private String IMAGE_HEX = "#000000";
    private String EXCEPTION_MESSAGE = "Granularity and number of colors to find must both be greater than 0";

    @Mock
    private BufferedImage mockImage;

    private ImageToHexValuesConverter sut = new ImageToHexValuesConverter();

    @Test
    public void whenGetHexPixelCountMap_givenProperImage_thenReturnsHexCountsMap() {
        when(mockImage.getHeight()).thenReturn(IMAGE_HEIGHT);
        when(mockImage.getWidth()).thenReturn(IMAGE_WIDTH);
        when(mockImage.getRGB(anyInt(), anyInt())).thenReturn(IMAGE_RGB);

        Map<String, Integer> result = sut.getHexPixelCountMap(mockImage, NUM_COLORS_TO_FIND, GRANULARITY);

        assertNotNull(result);
        assertTrue(!result.isEmpty());
        assertTrue(result.get(IMAGE_HEX) == (IMAGE_HEIGHT * IMAGE_WIDTH));
        assertTrue(result.keySet().size() == NUM_COLORS_TO_FIND);
    }

    @Test
    public void whenGetHexPixelCountMap_givenMoreColorsToFindThanPresentInImage_thenReturnsHexCountsMap() {
        when(mockImage.getHeight()).thenReturn(IMAGE_HEIGHT);
        when(mockImage.getWidth()).thenReturn(IMAGE_WIDTH);
        when(mockImage.getRGB(anyInt(), anyInt())).thenReturn(IMAGE_RGB);

        Map<String, Integer> result = sut.getHexPixelCountMap(mockImage, Integer.MAX_VALUE, GRANULARITY);

        assertNotNull(result);
        assertTrue(!result.isEmpty());
        assertTrue(result.get(IMAGE_HEX) == (IMAGE_HEIGHT * IMAGE_WIDTH));
        assertTrue(result.keySet().size() == NUM_COLORS_TO_FIND);
    }

    @Test
    public void whenGetHexPixelCountMap_givenNegativeGranularity_thenThrowsIllegalArgumentException() {
        Exception e = assertThrows(IllegalArgumentException.class,
                () -> sut.getHexPixelCountMap(mockImage, NUM_COLORS_TO_FIND, -GRANULARITY));
        assertTrue(e.getMessage().equals(EXCEPTION_MESSAGE));
    }

    @Test
    public void whenGetHexPixelCountMap_givenNegativeNumColorsToFind_thenThrowsIllegalArgumentException() {
        Exception e = assertThrows(IllegalArgumentException.class,
                () -> sut.getHexPixelCountMap(mockImage, -NUM_COLORS_TO_FIND, GRANULARITY));
        assertTrue(e.getMessage().equals(EXCEPTION_MESSAGE));
    }
}
