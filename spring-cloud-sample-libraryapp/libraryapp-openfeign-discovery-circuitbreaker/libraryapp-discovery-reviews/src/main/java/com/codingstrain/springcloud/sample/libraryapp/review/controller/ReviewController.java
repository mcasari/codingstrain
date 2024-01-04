package com.codingstrain.springcloud.sample.libraryapp.review.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codingstrain.springcloud.sample.libraryapp.model.entity.Review;
import com.codingstrain.springcloud.sample.libraryapp.review.service.ReviewService;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/review/{bookTitle}")
    public List<Review> findByBookTitle(@PathVariable("bookTitle") String bookTitle) {
        return reviewService.findByBookTitle(bookTitle);
    }

}