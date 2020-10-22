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
    private int IMAGE_HEIGHT = 5;
    private int IMAGE_WIDTH = 4;
    private int IMAGE_RGB = 0;
    private String IMAGE_HEX = "#000000";
    private String EXCEPTION_MESSAGE = "Granularity must be greater than 0";

    @Mock
    private BufferedImage mockImage;

    @Test
    public void whenGetHexPixelCountMap_givenProperImage_thenReturnsHexCountsMap() {
        when(mockImage.getHeight()).thenReturn(IMAGE_HEIGHT);
        when(mockImage.getWidth()).thenReturn(IMAGE_WIDTH);
        when(mockImage.getRGB(anyInt(), anyInt())).thenReturn(IMAGE_RGB);

        Map<String, Integer> result = ImageToHexValuesConverter.getHexPixelCountMap(mockImage, GRANULARITY);

        assertNotNull(result);
        assertTrue(!result.isEmpty());
        assertTrue(result.get(IMAGE_HEX) == (IMAGE_HEIGHT * IMAGE_WIDTH));
    }

    @Test
    public void whenGetHexPixelCountMap_givenNegativeGranularity_thenThrowsIllegalArgumentException() {
        Exception e = assertThrows(IllegalArgumentException.class,
                () -> ImageToHexValuesConverter.getHexPixelCountMap(mockImage, -GRANULARITY));
        assertTrue(e.getMessage().equals(EXCEPTION_MESSAGE));
    }
}
