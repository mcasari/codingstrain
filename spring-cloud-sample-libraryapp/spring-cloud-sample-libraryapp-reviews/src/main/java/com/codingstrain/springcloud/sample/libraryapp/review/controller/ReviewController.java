package com.codingstrain.springcloud.sample.libraryapp.review.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codingstrain.springcloud.sample.libraryapp.review.model.Review;
import com.codingstrain.springcloud.sample.libraryapp.review.service.ReviewService;

@RestController
@RequestMapping("/library")
public class ReviewController {

	@Autowired
	private ReviewService lendingService;

	@PostMapping(value="/lending", consumes = "application/json", produces = "application/json")
	public Review lend(@RequestBody Review lending) {
		return lendingService.lend(lending);
	}
	
	
}