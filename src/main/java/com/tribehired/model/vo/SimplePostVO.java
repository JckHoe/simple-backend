package com.tribehired.model.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SimplePostVO {
    private String postId;
    private int totalCount;
}
