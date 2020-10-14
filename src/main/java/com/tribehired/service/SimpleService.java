package com.tribehired.service;

import com.tribehired.model.integration.response.CommentResponse;
import com.tribehired.model.integration.response.PostResponse;
import com.tribehired.model.response.TopPostResponse;
import com.tribehired.model.vo.SimplePostVO;
import com.tribehired.service.integration.CommentsService;
import com.tribehired.service.integration.PostService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Slf4j
@Service
@AllArgsConstructor
public class SimpleService {
    private final CommentsService commentsService;
    private final PostService postService;

    public void getTopPost(TopPostResponse response) {
        PostResponse[] postResponse = postService.getAllPost();
        CommentResponse[] commentResponse = commentsService.getAllComments();
        if(postResponse!= null && commentResponse != null){
            SimplePostVO[] simplePostArray = convertToSimplePostArray(postResponse);
            System.out.println(Arrays.toString(simplePostArray));
        }
    }

    private SimplePostVO[] convertToSimplePostArray(PostResponse[] postResponse){
        SimplePostVO[] simplePostArray = new SimplePostVO[postResponse.length];

        for(int i=0;i<postResponse.length;i++){
            simplePostArray[i] = SimplePostVO.builder()
                    .postId(postResponse[i].getId())
                    .build();
        }

        return simplePostArray;
    }

    private void insertionSortDescending(SimplePostVO[] array) {
        for (int j = 1; j < array.length; j++) {
            SimplePostVO current = array[j];
            int i = j - 1;
            while ((i > -1) && (array[i].getTotalCount() < current.getTotalCount())) {
                array[i + 1] = array[i];
                i--;
            }
            array[i + 1] = current;
        }
    }
}
