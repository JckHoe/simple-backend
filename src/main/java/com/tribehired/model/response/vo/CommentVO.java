package com.tribehired.model.response.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CommentVO {
    @JsonProperty("post_id")
    private String postId;

    @JsonProperty("comment_body")
    private String body;

    @JsonProperty("comment_by")
    private String name;

    @JsonProperty("from_email")
    private String email;
}
