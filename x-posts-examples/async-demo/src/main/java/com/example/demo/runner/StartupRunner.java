
package com.example.demo.runner;

import com.example.demo.service.AsyncService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupRunner implements CommandLineRunner {

    private final AsyncService asyncService;

    public StartupRunner(AsyncService asyncService) {
        this.asyncService = asyncService;
    }

    @Override
    public void run(String... args) {
        for (int i = 1; i <= 10; i++) {
            asyncService.runTask(i);
        }
    }
}
