package com.tribehired.service;

import com.tribehired.model.integration.response.CommentResponse;
import com.tribehired.model.integration.response.PostResponse;
import com.tribehired.model.response.FilteredCommentListResponse;
import com.tribehired.model.response.TopPostResponse;
import com.tribehired.model.response.vo.CommentVO;
import com.tribehired.model.response.vo.FilterVO;
import com.tribehired.model.response.vo.PostVO;
import com.tribehired.model.vo.SimplePostVO;
import com.tribehired.service.integration.CommentsService;
import com.tribehired.service.integration.PostService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
            SimplePostVO[] simplePostArray = convertToSimplePostArrayForSorting(postResponse);

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

    public void getAllFilteredComment(FilteredCommentListResponse response, FilterVO filterVO){
        CommentResponse[] commentResponse = commentsService.getAllComments();
        List<CommentVO> commentVOList = new ArrayList<>();

        for(CommentResponse comment: commentResponse){
            if(StringUtils.containsAny(comment.getBody(), filterVO.getCommentKeyword())
                    || StringUtils.containsAny(comment.getEmail(), filterVO.getEmail())
                    || StringUtils.containsAny(comment.getName(), filterVO.getName())
            ){
                commentVOList.add(CommentVO.builder()
                        .postId(comment.getPostId())
                        .body(comment.getBody())
                        .email(comment.getEmail())
                        .name(comment.getName())
                        .build()
                );
            }

            // Scale max response size
            if(commentVOList.size() > filterVO.getPageSize()){
                break;
            }
        }

        response.setFilteredCommentList(commentVOList);
    }

    private SimplePostVO[] convertToSimplePostArrayForSorting(PostResponse[] postResponse){
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
