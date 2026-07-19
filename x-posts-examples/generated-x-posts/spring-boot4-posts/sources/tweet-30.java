// ✅ Boot 4
package com.example.env;

import org.springframework.boot.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;

public class RegionEnvironmentPostProcessor
        implements EnvironmentPostProcessor {

    @Override
    public void postProcessEnvironment(
            ConfigurableEnvironment env,
            SpringApplication application) {
        // add defaults before context refresh
    }
}

# META-INF/spring.factories
org.springframework.boot.EnvironmentPostProcessor=\
com.example.env.RegionEnvironmentPostProcessor
