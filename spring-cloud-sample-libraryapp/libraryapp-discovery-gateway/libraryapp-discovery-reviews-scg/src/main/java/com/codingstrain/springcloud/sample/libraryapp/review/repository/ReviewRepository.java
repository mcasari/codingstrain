package com.codingstrain.springcloud.sample.libraryapp.review.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.codingstrain.springcloud.sample.libraryapp.model.entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, String>, JpaSpecificationExecutor<Review> {

    List<Review> findAllByBookTitle(String title);
}
