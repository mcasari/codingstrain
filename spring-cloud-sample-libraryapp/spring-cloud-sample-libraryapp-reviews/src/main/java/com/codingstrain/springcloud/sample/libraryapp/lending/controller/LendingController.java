package com.codingstrain.springcloud.sample.libraryapp.lending.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codingstrain.springcloud.sample.libraryapp.lending.model.Lending;
import com.codingstrain.springcloud.sample.libraryapp.lending.service.LendingService;

@RestController
@RequestMapping("/library")
public class LendingController {

	@Autowired
	private LendingService lendingService;

	@PostMapping(value="/lending", consumes = "application/json", produces = "application/json")
	public Lending lend(@RequestBody Lending lending) {
		return lendingService.lend(lending);
	}
	
	
}