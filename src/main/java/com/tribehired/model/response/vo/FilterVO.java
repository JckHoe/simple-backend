package com.tribehired.model.response.vo;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class FilterVO {
    private String name;
    private String email;
    private String commentKeyword;
    private int pageSize;
}
