package com.hertzog.analyzer;

import org.springframework.lang.NonNull;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class ImageToHexValuesConverter {

    public static Map<String, Integer> getHexPixelCountMap(@NonNull BufferedImage image, int granularity) {
        if (granularity < 1) {
            throw new IllegalArgumentException("Granularity must be greater than 0");
        }

        Map<String, Integer> hexCountsMap = new HashMap<>();
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                String roundedHex = getRoundedHexFromRGB(image.getRGB(x, y), granularity);
                hexCountsMap.put(roundedHex, hexCountsMap.getOrDefault(roundedHex, 0) + 1);
            }
        }
        return hexCountsMap;
    }

    private static String getRoundedHexFromRGB(int rgb, int granularity) {
        int[] roundedRGB = roundRGBToGranularity(rgb, granularity);
        return String.format("#%02x%02x%02x", roundedRGB[0], roundedRGB[1], roundedRGB[2]);
    }

    private static int[] roundRGBToGranularity(int rgb, int granularity) {
        int red = (rgb >> 16) & 0xff;
        int green = (rgb >> 8) & 0xff;
        int blue = (rgb) & 0xff;

        return new int[]{roundNumberToGranularity(red, granularity),
                roundNumberToGranularity(green, granularity),
                roundNumberToGranularity(blue, granularity)};
    }

    private static int roundNumberToGranularity(int value, int granularity) {
        return (Math.round(value/granularity) * granularity);
    }
}