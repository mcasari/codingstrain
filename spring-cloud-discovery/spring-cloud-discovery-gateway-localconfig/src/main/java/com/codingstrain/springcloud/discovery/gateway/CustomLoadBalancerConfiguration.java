package com.codingstrain.springcloud.discovery.gateway;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.cloud.loadbalancer.cache.LoadBalancerCacheManager;
import org.springframework.cloud.loadbalancer.config.LoadBalancerZoneConfig;
import org.springframework.cloud.loadbalancer.core.CachingServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.core.DiscoveryClientServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.core.ZonePreferenceServiceInstanceListSupplier;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;


public class CustomLoadBalancerConfiguration {

//	@Bean
	public ServiceInstanceListSupplier discoveryClientServiceInstanceListSupplier(
			ReactiveDiscoveryClient discoveryClient, Environment environment, LoadBalancerZoneConfig zoneConfig,
			ApplicationContext context) {
		DiscoveryClientServiceInstanceListSupplier firstDelegate = new DiscoveryClientServiceInstanceListSupplier(
				discoveryClient, environment);
		ZonePreferenceServiceInstanceListSupplier delegate = new ZonePreferenceServiceInstanceListSupplier(
				firstDelegate, zoneConfig);
		ObjectProvider<LoadBalancerCacheManager> cacheManagerProvider = context
				.getBeanProvider(LoadBalancerCacheManager.class);
		if (cacheManagerProvider.getIfAvailable() != null) {
			return new CachingServiceInstanceListSupplier(delegate, cacheManagerProvider.getIfAvailable());
		}
		return delegate;
	}
}