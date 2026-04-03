package com.example.demo;

import org.springframework.stereotype.Component;

@Component
public class MyController {

    private final MyService myService;

    public MyController(MyService myService) {
        this.myService = myService;
    }

    public String fetch() {
        return myService.getData();
    }
}
