package com.codingstrain.springcloud.sample.libraryapp.authors.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.codingstrain.springcloud.sample.libraryapp.authors.service.AuthorService;
import com.codingstrain.springcloud.sample.libraryapp.model.entity.Author;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private static final Logger log = LoggerFactory.getLogger(AuthorController.class);

    @Autowired
    private AuthorService authorService;

    @GetMapping("/author/{name}")
    public Optional<Author> findByName(@PathVariable("name") String name) {
        log.info("Got a request");
        return authorService.findByName(name);
    }

    @GetMapping("/getInstance")
    public String getInstance() {
        log.info("Got a request");
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String instanceInfo = request.getServerName() + ":" + request.getServerPort() + "";
        return "author-service instance: " + instanceInfo;
    }

}