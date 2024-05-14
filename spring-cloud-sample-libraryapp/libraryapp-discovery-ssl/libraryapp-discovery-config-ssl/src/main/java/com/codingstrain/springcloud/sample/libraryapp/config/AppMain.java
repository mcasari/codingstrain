package com.codingstrain.springcloud.sample.libraryapp.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class AppMain {


    public static void main(String[] args) {
        SpringApplication.run(AppMain.class, args);
    }


    //    @Bean
    //    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    //
    //        http.authorizeHttpRequests(auth -> auth.requestMatchers("/decrypt")
    //            .denyAll()
    //            .requestMatchers("/encrypt")
    //            .permitAll())
    //            .httpBasic(withDefaults());
    //
    //        return http.build();
    //    }



}
