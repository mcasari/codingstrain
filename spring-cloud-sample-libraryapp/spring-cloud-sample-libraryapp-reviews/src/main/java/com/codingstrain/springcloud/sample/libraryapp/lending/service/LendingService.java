package com.codingstrain.springcloud.sample.libraryapp.lending.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codingstrain.springcloud.sample.libraryapp.lending.model.Lending;
import com.codingstrain.springcloud.sample.libraryapp.lending.repository.LendingRepository;

@Service("lendingService")
public class LendingService {

	Logger logger = LoggerFactory.getLogger(LendingService.class);
	
	@Autowired
	private LendingRepository lendingRepository;

	public Lending lend(Lending lending) {
		return lendingRepository.save(lending);
	}
	
}
