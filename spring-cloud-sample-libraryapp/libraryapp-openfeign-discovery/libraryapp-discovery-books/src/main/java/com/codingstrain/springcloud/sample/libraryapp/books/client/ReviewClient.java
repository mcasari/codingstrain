package com.codingstrain.springcloud.sample.libraryapp.books.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.codingstrain.springcloud.sample.libraryapp.books.model.Review;


@FeignClient(name = "review-service")
public interface ReviewClient {

    @GetMapping("/review/{bookTitle}")
    public List<Review> findByBookTitle(@PathVariable("bookTitle") String bookTitle);
	
}