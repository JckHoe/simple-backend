package com.tribehired.model.integration.response;

import lombok.Data;

@Data
public class CommentResponse {
    private String postId;
    private String id;
    private String name;
    private String email;
    private String body;
}
