package com.codingstrain.springcloud.sample.libraryapp.books;

import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadBalancerConfiguration {

    //    @Bean
    //    public ServiceInstanceListSupplier discoveryClientServiceInstanceListSupplier(ConfigurableApplicationContext context) {
    //        return ServiceInstanceListSupplier.builder()
    //            .withBlockingDiscoveryClient()
    //            .withSameInstancePreference()
    //            .build(context);
    //    }
}