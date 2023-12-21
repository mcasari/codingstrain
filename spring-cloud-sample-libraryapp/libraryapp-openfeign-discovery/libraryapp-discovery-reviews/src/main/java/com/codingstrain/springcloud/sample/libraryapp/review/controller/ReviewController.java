package com.codingstrain.springcloud.sample.libraryapp.review.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codingstrain.springcloud.sample.libraryapp.review.model.Review;
import com.codingstrain.springcloud.sample.libraryapp.review.rest.ReviewRESTService;
import com.codingstrain.springcloud.sample.libraryapp.review.service.ReviewService;

@RestController
@RequestMapping("/library")
public class ReviewController implements ReviewRESTService {

    @Autowired
    private ReviewService reviewService;

    @Override
    public List<Review> findByBookTitle(@PathVariable("bookTitle") String bookTitle) {
        return reviewService.findByBookTitle(bookTitle);
    }

}