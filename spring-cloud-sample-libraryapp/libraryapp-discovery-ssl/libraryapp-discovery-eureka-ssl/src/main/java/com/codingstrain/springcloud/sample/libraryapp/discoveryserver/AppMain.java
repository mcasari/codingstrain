package com.codingstrain.springcloud.sample.libraryapp.discoveryserver;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;



@SpringBootApplication
@EnableEurekaServer
public class AppMain {

	public static void main(String[] args) {
        new SpringApplicationBuilder(AppMain.class).run(args);
	}

    //    @Bean
    //    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    //        return http.requiresChannel(channel -> channel.anyRequest()
    //            .requiresSecure())
    //            .authorizeHttpRequests(authorize -> authorize.anyRequest()
    //                .permitAll())
    //            .build();
    //    }

}
