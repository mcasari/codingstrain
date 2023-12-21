package com.codingstrain.springcloud.sample.libraryapp.books.client;

import org.springframework.cloud.openfeign.FeignClient;

import com.codingstrain.springcloud.sample.libraryapp.authors.rest.AuthorRESTService;


@FeignClient(name = "author-service")
public interface AuthorClient extends AuthorRESTService {
	
}