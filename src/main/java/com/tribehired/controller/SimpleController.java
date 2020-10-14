package com.tribehired.controller;

import com.tribehired.model.response.FilteredCommentListResponse;
import com.tribehired.model.response.TopPostResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.tribehired.constant.ApiConstant.API_PATH_GET_FILTERED_COMMENT_LIST;
import static com.tribehired.constant.ApiConstant.API_PATH_GET_TOP_POST;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@AllArgsConstructor
public class SimpleController extends BaseController {

    @GetMapping(value = API_PATH_GET_TOP_POST, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<TopPostResponse> getTopPost() {
        TopPostResponse response = new TopPostResponse();
        return new ResponseEntity<>(response, OK);
    }

    @GetMapping(value = API_PATH_GET_FILTERED_COMMENT_LIST, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<FilteredCommentListResponse> getFilteredCommentList() {
        FilteredCommentListResponse response = new FilteredCommentListResponse();
        return new ResponseEntity<>(response, OK);
    }
}
