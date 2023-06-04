package com.codingstrain.springcloud.discovery.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;

@RestController
public class ClientController {
	
	@Autowired
	private EurekaClient eurekaClient;
	
	@Autowired
	private DiscoveryClient discoveryClient;
			         
	@Value("${myproperty}")
	private String myproperty;

	@GetMapping("/myproperty")
	public String myproperty() {
		return "myproperty value: " + myproperty;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("/testEurekaClient")
	public String testEurekaClient() {
		Application application = eurekaClient.getApplication("CLIENT-SERVICE");
		List<InstanceInfo> instanceInfos = application.getInstances();
	    if (instanceInfos != null && instanceInfos.size() > 0 ) {
	        return instanceInfos.get(0).getHomePageUrl();
	    }
	    return null;
	}
	
	@GetMapping("/testDiscoveryClient")
	public String testDiscoveryClient() {
	    List<ServiceInstance> serviceInstances = discoveryClient.getInstances("CLIENT-SERVICE");
	    if (serviceInstances != null && serviceInstances.size() > 0 ) {
	        return serviceInstances.get(0).getServiceId();
	    }
	    return null;
	}
	
	
}