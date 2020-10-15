package com.tribehired.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Configuration
public class ApiConfig {
    @Bean(name = "restTemplate")
    public RestTemplate getRestTemplate(){
        RestTemplate restClient = new RestTemplate(
                new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory())
        );

        restClient.setInterceptors(Collections.singletonList(
                (request, body, execution) -> execution.execute(request, body)
        ));
        return restClient;
    }

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }
}
