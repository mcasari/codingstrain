package com.codingstrain.springcloud.config.client;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;


@Component
@RefreshScope 
@ConfigurationProperties(prefix = "myproperties")
public class DemoClient {
	private List<String> properties = new ArrayList<String>();

	public List<String> getProperties() {
		return properties;
	}

	@Value("${myproperty}")
	private String myproperty;

	public String getMyproperty() {
		return myproperty;
	}


}
