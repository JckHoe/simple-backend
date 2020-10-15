package com.tribehired.model.response.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PostVO {
    @JsonProperty("post_id")
    private String postId;

    @JsonProperty("post_title")
    private String postTitle;

    @JsonProperty("post_body")
    private String postBody;

    @JsonProperty("total_number_of_comments")
    private int totalComments;
}
