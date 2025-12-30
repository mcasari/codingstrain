package com.example.demo.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {

    @Cacheable(value = "weather", key = "#city")
    public String getWeather(String city) {
        simulateSlowApiCall();
        return "Sunny in " + city + " at " + System.currentTimeMillis();
    }

    private void simulateSlowApiCall() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
