package com.codingstrain.springcloud.sample.libraryapp.authors.controller;

import java.util.Optional;

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

    @Autowired
    private AuthorService authorService;

    @GetMapping("/author/{name}")
    public Optional<Author> findByName(@PathVariable("name") String name) {
        return authorService.findByName(name);
    }

    @GetMapping("/getInstance")
    public String getInstance() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String instanceInfo = request.getServerName() + ":" + request.getServerPort() + "";
        return "author-service instance: " + instanceInfo;
    }

}