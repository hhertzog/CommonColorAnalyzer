package com.hertzog.analyzer;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CommonColorsManagerTests {
    private int GRANULARITY = 10;
    private int NUM_COLORS_TO_FIND = 5;
    private String DIRECTORY_PATH = "C:\\Users\\hhert\\IdeaProjects\\CommonColorAnalyzer\\src\\test\\java\\com\\hertzog\\analyzer\\images";
    private String BAD_DIRECTORY_PATH = "not a directory";
    private String NO_IMAGES_DIRECTORY = "C:\\Users\\hhert\\IdeaProjects\\CommonColorAnalyzer\\src\\test\\java\\com\\hertzog\\analyzer\\imageless";
    private String EXCEPTION_MESSAGE = "\"" + BAD_DIRECTORY_PATH + "\" not a valid directory path";

    @Mock
    private ImageToHexValuesConverter converter;

    @Mock
    private CommonColorsView view;

    @InjectMocks
    private CommonColorsManager sut = new CommonColorsManager(converter, view, NUM_COLORS_TO_FIND, GRANULARITY);

    @Test
    public void whenDrawColorChartForAllImagesInDirectory_givenGoodDirectory_thenCallsRightMethods() {
        when(converter.getHexPixelCountMap(any(), anyInt(), anyInt())).thenReturn(new HashMap<>());
        when(converter.getHexPercentagesMap(any(), anyInt(), anyInt())).thenReturn(new HashMap<>());

        sut.drawColorChartForAllImagesInDirectory(DIRECTORY_PATH);

        verify(converter, times(2)).getHexPixelCountMap(any(), anyInt(), anyInt());
        verify(converter).getPercentageMapFromPixelCounts(any());
        verify(view).drawCommonImageColorsFromPercentageMap(anyMap());
    }

    @Test
    public void whenDrawColorChartForAllImagesInDirectory_givenImagelessDirectory_thenCallsRightMethods() {
        when(converter.getHexPixelCountMap(any(), anyInt(), anyInt())).thenReturn(new HashMap<>());
        when(converter.getHexPercentagesMap(any(), anyInt(), anyInt())).thenReturn(new HashMap<>());

        sut.drawColorChartForAllImagesInDirectory(NO_IMAGES_DIRECTORY);

        verify(converter).getPercentageMapFromPixelCounts(any());
        verify(view).drawCommonImageColorsFromPercentageMap(anyMap());
    }

    @Test
    public void whenDrawColorChartForAllImagesInDirectory_givenBadDirectory_thenThrowsIllegalArgumentException() {
        Exception e = assertThrows(IllegalArgumentException.class,
                () -> sut.drawColorChartForAllImagesInDirectory(BAD_DIRECTORY_PATH));

        assertEquals(e.getMessage(), EXCEPTION_MESSAGE);
    }
}