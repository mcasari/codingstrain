package com.codingstrain.springcloud.discovery.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.EurekaClient;

@RestController
public class ClientController {
	
	@Autowired
	private EurekaClient eurekaClient;
	
	@Autowired
	private DiscoveryClient discoveryClient;
			         
	@Value("${spring.config.activate.on-profiles}")
	private String zone;

	@GetMapping("/checkZone")
	public String ping() {
		return "This service runs in zone " + zone;
	}
	
	@GetMapping("/testEurekaClient")
	public String testEurekaClient() {
	    List<InstanceInfo> list = eurekaClient.getInstancesById("STORES");
	    if (list != null && list.size() > 0 ) {
	        return list.get(0).getHomePageUrl();
	    }
	    return null;
	}
	
	@GetMapping("/testDiscoveryClient")
	public String testDiscoveryClient() {
	    List<InstanceInfo> list = discoveryClient.getInstancesById("STORES");
	    if (list != null && list.size() > 0 ) {
	        return list.get(0).getHomePageUrl();
	    }
	    return null;
	}
	
	
}