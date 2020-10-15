package com.tribehired.service;

import com.tribehired.model.integration.response.CommentResponse;
import com.tribehired.model.integration.response.PostResponse;
import com.tribehired.model.response.TopPostResponse;
import com.tribehired.model.response.vo.PostVO;
import com.tribehired.model.vo.SimplePostVO;
import com.tribehired.service.integration.CommentsService;
import com.tribehired.service.integration.PostService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

            for (SimplePostVO simplePostVO : simplePostArray) {
                for (CommentResponse comment : commentResponse) {
                    if (comment.getPostId().equals(simplePostVO.getPostId()))
                        simplePostVO.setTotalCount(simplePostVO.getTotalCount() + 1);
                }
            }

            insertionSortDescending(simplePostArray);

            List<PostVO> postVOList = new ArrayList<>();
            for (SimplePostVO simplePostVO: simplePostArray){
                for(PostResponse post : postResponse) {
                    if(post.getId().equals(simplePostVO.getPostId())) {
                        postVOList.add(PostVO.builder()
                                .postId(simplePostVO.getPostId())
                                .postBody(post.getBody())
                                .postTitle(post.getTitle())
                                .totalComments(simplePostVO.getTotalCount())
                                .build()
                        );
                    }
                }
            }
            response.setPostVOList(postVOList);
        }
    }

    private SimplePostVO[] convertToSimplePostArray(PostResponse[] postResponse){
        SimplePostVO[] simplePostArray = new SimplePostVO[postResponse.length];

        for(int i=0;i<postResponse.length;i++){
            simplePostArray[i] = SimplePostVO.builder()
                    .postId(postResponse[i].getId())
                    .totalCount(0)
                    .build();
        }

        return simplePostArray;
    }

    private void insertionSortDescending(SimplePostVO[] simplePostArray) {
        for (int j = 1; j < simplePostArray.length; j++) {
            SimplePostVO current = simplePostArray[j];
            int i = j - 1;
            while ((i > -1) && (simplePostArray[i].getTotalCount() < current.getTotalCount())) {
                simplePostArray[i + 1] = simplePostArray[i];
                i--;
            }
            simplePostArray[i + 1] = current;
        }
    }
}
