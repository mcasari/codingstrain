package com.codingstrain.springcloud.sample.libraryapp.review.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codingstrain.springcloud.sample.libraryapp.review.model.Review;
import com.codingstrain.springcloud.sample.libraryapp.review.repository.ReviewRepository;

@Service("lendingService")
public class ReviewService {

	Logger logger = LoggerFactory.getLogger(ReviewService.class);
	
	@Autowired
	private ReviewRepository lendingRepository;

	public Review lend(Review lending) {
		return lendingRepository.save(lending);
	}
	
}
