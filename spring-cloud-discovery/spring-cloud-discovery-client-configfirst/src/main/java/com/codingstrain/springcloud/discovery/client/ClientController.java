package com.codingstrain.springcloud.discovery.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
			         
	@Value("${myproperty}")
	private String myproperty;

	@GetMapping("/myproperty")
	public String myproperty() {
		return "myproperty value: " + myproperty;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("/testEurekaClient")
	public String testEurekaClient() {
	    List<InstanceInfo> list = eurekaClient.getInstancesById("CLIENT_SERVICE");
	    if (list != null && list.size() > 0 ) {
	        return list.get(0).getHomePageUrl();
	    }
	    return null;
	}
	
	@GetMapping("/testDiscoveryClient")
	public String testDiscoveryClient() {
	    List<InstanceInfo> list = discoveryClient.getInstancesById("CLIENT_SERVICE");
	    if (list != null && list.size() > 0 ) {
	        return list.get(0).getHomePageUrl();
	    }
	    return null;
	}
	
	
}