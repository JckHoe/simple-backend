package com.tribehired.service.integration;

import com.tribehired.model.integration.response.CommentResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Slf4j
@Service
public class CommentsService {

    @Value("${integration.comments.url}")
    private String commentsUrl;

    @Qualifier("restTemplate")
    private final RestTemplate restTemplate;

    public CommentsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public CommentResponse[] getAllComments() {
        CommentResponse[] commentResponse = null;
        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setAccept(Collections.singletonList(MediaType.ALL));
            HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
            ResponseEntity<CommentResponse[]> responseEntity = restTemplate.exchange(commentsUrl, HttpMethod.GET, httpEntity, CommentResponse[].class);

            commentResponse = responseEntity.getBody();
        } catch (Exception e) {
            log.error("GetAllComments - ", e);
        }

        return commentResponse;
    }
}
