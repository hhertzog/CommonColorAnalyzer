package com.hertzog.analyzer;

import org.springframework.lang.NonNull;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class CommonColorsManager {
    private ImageToHexValuesConverter converter;
    private CommonColorsView view;
    public int numColorsToFind;
    public int granularity;

    public CommonColorsManager(@NonNull ImageToHexValuesConverter converter,
                               @NonNull CommonColorsView view,
                               int numColorsToFind,
                               int granularity) {
        this.converter = converter;
        this.view = view;
        this.numColorsToFind = numColorsToFind;
        this.granularity = granularity;
    }

    public void drawColorChartForAllImagesInDirectory(@NonNull String directory) {
        Map<String, Double> percentageMap =
                converter.getPercentageMapFromPixelCounts(getAllDirectoryFilesCombinedPixelCountsMap(directory));

        view.drawCommonImageColorsFromPercentageMap(percentageMap);
    }

    private Map<String, Integer> getAllDirectoryFilesCombinedPixelCountsMap(String directory) {
        List<BufferedImage> images = getDirectoryFilesAsImages(directory);
        Map<String, Integer> allImagePixelCounts = new HashMap<>();

        for (BufferedImage image : images) {
            combinePixelCountMaps(allImagePixelCounts,
                    converter.getHexPixelCountMap(image, numColorsToFind, granularity));
        }
        return allImagePixelCounts;
    }

    private List<BufferedImage> getDirectoryFilesAsImages(String directory) {
        File[] files = getAllFilesFromDirectory(directory);
        System.out.println(Arrays.toString(files));

        List<BufferedImage> images = new ArrayList<>();
        for (File file : files) {
            images.add(getSingleImageFromFile(file));
        }
        return images;
    }

    private File[] getAllFilesFromDirectory(String directoryPath) {
        File[] files = new File(directoryPath).listFiles();
        if (files == null) {
            throw new IllegalArgumentException("\"" + directoryPath + "\" not a valid directory path");
        }

        return files;
    }

    private void combinePixelCountMaps(Map<String, Integer> map, Map<String, Integer> mapToAdd) {
        // adds mapToAdd to the map given as the first parameter
        for (Map.Entry<String, Integer> entry : mapToAdd.entrySet()) {
            map.put(entry.getKey(), map.getOrDefault(entry.getKey(), 0) + entry.getValue());
        }
    }

    private BufferedImage getSingleImageFromFile(File file) {
        try {
            return ImageIO.read(file);
        } catch (IOException e) { // don't stop the whole process - just skip this image
            System.err.println("Could not find image at " + file.getAbsolutePath());
            return null;
        }
    }
}
