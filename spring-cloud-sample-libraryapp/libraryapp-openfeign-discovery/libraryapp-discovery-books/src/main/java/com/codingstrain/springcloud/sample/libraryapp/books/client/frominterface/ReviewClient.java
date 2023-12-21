package com.codingstrain.springcloud.sample.libraryapp.books.client.frominterface;

import org.springframework.cloud.openfeign.FeignClient;

import com.codingstrain.springcloud.sample.libraryapp.review.rest.ReviewRESTService;


@FeignClient(name = "review-service")
public interface ReviewClient extends ReviewRESTService {
	
}