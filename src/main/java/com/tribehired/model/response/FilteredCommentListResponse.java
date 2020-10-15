package com.tribehired.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tribehired.model.response.vo.CommentVO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FilteredCommentListResponse {
    private List<CommentVO> filteredCommentList;
}
