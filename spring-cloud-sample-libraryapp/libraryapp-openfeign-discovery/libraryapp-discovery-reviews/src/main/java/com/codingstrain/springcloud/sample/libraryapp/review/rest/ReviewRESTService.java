package com.codingstrain.springcloud.sample.libraryapp.review.rest;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.codingstrain.springcloud.sample.libraryapp.review.model.Review;

public interface ReviewRESTService {

    @GetMapping("/review/{bookTitle}")
    public List<Review> findByBookTitle(@PathVariable("bookTitle") String bookTitle);
	
}