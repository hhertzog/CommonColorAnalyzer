package com.hertzog.analyzer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@ComponentScan("com.hertzog.analyzer")
@PropertySource("classpath:application.properties")
public class Config {
    @Autowired
    private Environment env;

    @Bean
    public CommonColorsManager commonColorsManager() {
        return new CommonColorsManager(imageToHexValuesConverter(),
                commonColorsView(),
                numColorsToFind(),
                granularity());
    }

    @Bean
    public CommonColorsView commonColorsView() {
        return new CommonColorsView(imageToHexValuesConverter());
    }

    @Bean
    public ImageToHexValuesConverter imageToHexValuesConverter() {
        return new ImageToHexValuesConverter();
    }

    private int numColorsToFind() {
        return Integer.parseInt(getConfigValue("numColorsToFind"));
    }

    private int granularity() {
        return Integer.parseInt(getConfigValue("granularity"));
    }

    private String getConfigValue(String configKey){
        return env.getProperty(configKey);
    }

}
