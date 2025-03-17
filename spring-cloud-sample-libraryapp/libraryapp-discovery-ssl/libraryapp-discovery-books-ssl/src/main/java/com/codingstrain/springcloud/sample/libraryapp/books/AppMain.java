package com.codingstrain.springcloud.sample.libraryapp.books;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@RefreshScope
public class AppMain {

    @Value("${eureka.client.tls.trust-store}")
    private String trustStore;

    @Value("${eureka.client.tls.trust-store-password}")
    private String trustStorePassword;

    public static void main(String[] args) {
        SpringApplication.run(AppMain.class, args);
    }


    @Bean
    public RestTemplate restTemplate() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, CertificateException, MalformedURLException,
                                       IOException {

        URL url = new URL(trustStore);
        SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(url, trustStorePassword.toCharArray())
            .build();
        SSLConnectionSocketFactory sslConFactory = new SSLConnectionSocketFactory(sslContext);
        HttpClientConnectionManager cm = PoolingHttpClientConnectionManagerBuilder.create()
            .setSSLSocketFactory(sslConFactory)
            .build();
        CloseableHttpClient httpClient = HttpClients.custom()
            .setConnectionManager(cm)
            .build();
        ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        return new RestTemplate(requestFactory);
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
