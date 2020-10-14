package com.tribehired.service.integration;

import com.tribehired.model.integration.response.PostResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Slf4j
@Service
public class PostService {
    @Value("${integration.single-post.url}")
    private String singlePostUrl;

    @Value("${integration.post.url}")
    private String allPostUrl;

    @Value("${placeholder.path-param.single-post}")
    private String pathParam;

    @Qualifier("restTemplate")
    private final RestTemplate restTemplate;

    public PostService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public PostResponse getSinglePost(String postId){
        PostResponse postResponse = null;
        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setAccept(Collections.singletonList(MediaType.ALL));
            HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
            ResponseEntity<PostResponse> responseEntity = restTemplate.exchange(
                    singlePostUrl.replace(pathParam, postId),
                    HttpMethod.GET,
                    httpEntity,
                    PostResponse.class
            );

            postResponse = responseEntity.getBody();
        } catch (Exception e){
            log.error("GetSinglePost - ", e);
        }
        return postResponse;
    }

    public PostResponse[] getAllPost(){
        PostResponse[] postResponse = null;
        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setAccept(Collections.singletonList(MediaType.ALL));
            HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
            ResponseEntity<PostResponse[]> responseEntity = restTemplate.exchange(
                    allPostUrl,
                    HttpMethod.GET,
                    httpEntity,
                    PostResponse[].class
            );

            postResponse = responseEntity.getBody();
        } catch (Exception e){
            log.error("GetSinglePost - ", e);
        }

        return postResponse;
    }
}
