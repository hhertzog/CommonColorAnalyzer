package com.hertzog.analyzer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.lang.NonNull;

@SpringBootApplication
public class CommonColorAnalyzerApplication implements CommandLineRunner {
	// Path to directory containing images to analyze
	private String IMAGES_DIRECTORY_PATH = "C:\\Users\\hhert\\IdeaProjects\\CommonColorAnalyzer\\src\\main\\java\\com\\hertzog\\analyzer\\images";

	private CommonColorsManager manager;

	@Autowired
	public CommonColorAnalyzerApplication(@NonNull CommonColorsManager manager) {
		this.manager = manager;
	}

	public static void main(String[] args) {
		SpringApplication.run(CommonColorAnalyzerApplication.class, args);
	}

	@Override
	public void run(String... args) {
		System.setProperty("java.awt.headless", "false");
		drawCommonColors();
	}

	private void drawCommonColors() {
		manager.drawColorChartForAllImagesInDirectory(IMAGES_DIRECTORY_PATH);
	}
}
