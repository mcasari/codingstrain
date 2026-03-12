package com.example.loggingdemo.controller;

import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class DemoController {

    @PostMapping("/hello")
    public Map<String, Object> hello(@RequestBody Map<String, Object> body) {

        return Map.of(
                "message", "Request received",
                "data", body
        );
    }

}