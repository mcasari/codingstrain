package com.codingstrain.springcloud.config.client.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codingstrain.springcloud.config.client.DemoClient;

@RestController
public class ConfigClientController {

	private static final Logger LOG = LoggerFactory.getLogger(ConfigClientController.class);
	
	@Autowired
	private DemoClient demoClient;
	
	@GetMapping("/getProperties")
	public List<String> getProperties() {
		LOG.info("Properties: " + demoClient.getProperties().toString());
		return demoClient.getProperties();
	}
	
	@GetMapping("/getProperty")
	public String getProperty() {
		LOG.info("Property: " + demoClient.getMyproperty().toString());
		return demoClient.getMyproperty();
	}
}
