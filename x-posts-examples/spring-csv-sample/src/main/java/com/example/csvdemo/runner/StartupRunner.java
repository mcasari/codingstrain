
package com.example.csvdemo.runner;

import com.example.csvdemo.service.CsvService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
public class StartupRunner implements CommandLineRunner {

    private final CsvService csvService;

    public StartupRunner(CsvService csvService) {
        this.csvService = csvService;
    }

    @Override
    public void run(String... args) {
        Path path = Path.of("data/sample.csv");
        csvService.process(path);
    }
}
