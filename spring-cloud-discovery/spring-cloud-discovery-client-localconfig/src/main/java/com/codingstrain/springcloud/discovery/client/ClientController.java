package com.codingstrain.springcloud.discovery.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController {
			         
	@Value("${spring.config.activate.on-profiles}")
	private String zone;

	@GetMapping("/checkZone")
	public String ping() {
		return "This service runs in zone " + zone;
	}
	
}