package com.codingstrain.springcloud.sample.libraryapp.review.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codingstrain.springcloud.sample.libraryapp.review.model.Review;
import com.codingstrain.springcloud.sample.libraryapp.review.service.ReviewService;

@RestController
@RequestMapping("/library")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping(value = "/review", consumes = "application/json", produces = "application/json")
    public List<Review> findByBookTitle(@PathVariable("bookTitle") String bookTitle) {
        return reviewService.findByBookTitle(bookTitle);
    }

}