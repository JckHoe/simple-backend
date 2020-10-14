package com.tribehired.model.integration.response;

import lombok.Data;

@Data
public class PostResponse {
    private String userId;
    private String id;
    private String title;
    private String body;
}
