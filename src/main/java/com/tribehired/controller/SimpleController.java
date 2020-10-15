package com.tribehired.controller;

import com.tribehired.model.response.FilteredCommentListResponse;
import com.tribehired.model.response.TopPostResponse;
import com.tribehired.model.response.vo.FilterVO;
import com.tribehired.service.SimpleService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.tribehired.constant.ApiConstant.API_PATH_GET_FILTERED_COMMENT_LIST;
import static com.tribehired.constant.ApiConstant.API_PATH_GET_TOP_POST;
import static com.tribehired.constant.CommonConstant.DEFAULT_PAGE_SIZE;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@AllArgsConstructor
public class SimpleController extends BaseController {
    private final SimpleService simpleService;

    @GetMapping(value = API_PATH_GET_TOP_POST, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<TopPostResponse> getTopPost() {
        TopPostResponse response = new TopPostResponse();
        simpleService.getTopPost(response);
        return new ResponseEntity<>(response, OK);
    }

    @GetMapping(value = API_PATH_GET_FILTERED_COMMENT_LIST, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<FilteredCommentListResponse> getFilteredCommentList(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "comment", required = false) String comment,
            @RequestParam(value = "id", required = false) String id,
            @RequestParam(value = "size", required = false) String pageSize,
            @RequestParam(value = "pageNo", required = false) String pageNo
    ) {
        FilterVO filterVO = FilterVO.builder()
                .name(name)
                .email(email)
                .commentKeyword(comment)
                .id(id)
                .pageSize(
                        StringUtils.isNumeric(pageSize)
                                ? Integer.parseInt(pageSize)
                                : DEFAULT_PAGE_SIZE
                )
                .pageNo(
                        StringUtils.isNumeric(pageNo)
                                ? Integer.parseInt(pageNo)
                                : 1
                )
                .build();
        FilteredCommentListResponse response = new FilteredCommentListResponse();
        simpleService.getAllFilteredComment(response, filterVO);
        return new ResponseEntity<>(response, OK);
    }
}
