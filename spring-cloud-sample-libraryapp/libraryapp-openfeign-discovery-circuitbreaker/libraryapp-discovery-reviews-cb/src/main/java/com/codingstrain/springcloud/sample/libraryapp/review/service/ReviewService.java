package com.codingstrain.springcloud.sample.libraryapp.review.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codingstrain.springcloud.sample.libraryapp.model.entity.Review;
import com.codingstrain.springcloud.sample.libraryapp.review.repository.ReviewRepository;

@Service("reviewService")
public class ReviewService {

    Logger logger = LoggerFactory.getLogger(ReviewService.class);

    @Autowired
    private ReviewRepository reviewRepository;

    public List<Review> findByBookTitle(String booktitle) {
        return reviewRepository.findAllByBookTitle(booktitle);
    }

}
