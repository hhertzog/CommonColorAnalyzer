package com.hertzog.analyzer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CommonColorAnalyzerApplicationTests {

	@BeforeEach
	public void setUp() {
		System.setProperty("java.awt.headless", "false");
	}

	@Test
	void contextLoads() {
	}

}
