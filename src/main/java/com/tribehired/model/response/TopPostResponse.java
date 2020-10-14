package com.tribehired.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tribehired.model.response.vo.PostVO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TopPostResponse extends BasePaginationResponse {
    @JsonProperty("topPostList")
    private List<PostVO> postVOList;
}
