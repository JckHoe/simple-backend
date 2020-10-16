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
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class SimpleService {
    private final CommentsService commentsService;
    private final PostService postService;

    public void getTopPost(TopPostResponse response) {
        PostResponse[] postResponse = postService.getAllPost();
        CommentResponse[] commentResponse = commentsService.getAllComments();
        if (postResponse != null && commentResponse != null) {
            SimplePostVO[] simplePostArray = convertToSimplePostArrayForSorting(postResponse);

            for (SimplePostVO simplePostVO : simplePostArray) {
                for (CommentResponse comment : commentResponse) {
                    if (comment.getPostId().equals(simplePostVO.getPostId()))
                        simplePostVO.setTotalCount(simplePostVO.getTotalCount() + 1);
                }
            }

            insertionSortDescending(simplePostArray);

            List<PostVO> postVOList = new ArrayList<>();
            for (SimplePostVO simplePostVO : simplePostArray) {
                for (PostResponse post : postResponse) {
                    if (post.getId().equals(simplePostVO.getPostId())) {
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

    public void getAllFilteredComment(FilteredCommentListResponse response, FilterVO filterVO) {
        CommentResponse[] commentResponse = commentsService.getAllComments();
        List<CommentVO> commentVOList = new ArrayList<>();
        String[] stringArray = new String[]{"2", "1", "8"};

        for (CommentResponse comment : commentResponse) {
//            if (StringUtils.contains(comment.getBody(), filterVO.getCommentKeyword())
//                    || StringUtils.contains(comment.getEmail(), filterVO.getEmail())
//                    || StringUtils.contains(comment.getName(), filterVO.getName())
//                    || StringUtils.equals(comment.getId(), filterVO.getId())
//                    || (StringUtils.isEmpty(filterVO.getName()) && StringUtils.isEmpty(filterVO.getEmail()) && StringUtils.isEmpty(filterVO.getCommentKeyword()))
//            ) {
            if (((StringUtils.contains(comment.getBody(), filterVO.getCommentKeyword()) || StringUtils.isEmpty(filterVO.getCommentKeyword()))
                    && (StringUtils.contains(comment.getEmail(), filterVO.getEmail()) || StringUtils.isEmpty(filterVO.getEmail()))
                    && (StringUtils.contains(comment.getName(), filterVO.getName()) || StringUtils.isEmpty(filterVO.getName()))
                    && (StringUtils.equals(comment.getId(), filterVO.getId()) || StringUtils.isEmpty(filterVO.getId()))
                    || (StringUtils.isEmpty(filterVO.getName()) && StringUtils.isEmpty(filterVO.getEmail()) && StringUtils.isEmpty(filterVO.getCommentKeyword())))
                    && !ArrayUtils.contains(stringArray, comment.getPostId())
            ) {
                commentVOList.add(CommentVO.builder()
                        .postId(comment.getPostId())
                        .body(comment.getBody())
                        .email(comment.getEmail())
                        .name(comment.getName())
                        .build()
                );
            }
        }

        int skipCount = (filterVO.getPageNo() - 1) * filterVO.getPageSize();
        List<CommentVO> paginatedCommentList = new ArrayList<>();
        int remainSize = commentVOList.size() - skipCount;
        try {
            if (remainSize > 0) {
                paginatedCommentList = commentVOList.stream()
                        .skip(skipCount)
                        .collect(Collectors.toList())
                        .subList(0, Math.min(filterVO.getPageSize(), remainSize));
            }
        } catch (Exception e) {
            log.info("Exceed page no");
        }

        response.setFilteredCommentList(paginatedCommentList);
        response.setTotalRecord(commentVOList.size());
        response.setTotalPage((int) Math.ceil(
                Double.parseDouble(String.valueOf(commentVOList.size())) / Double.parseDouble(String.valueOf(filterVO.getPageSize()))
        ));
        response.setPageNo(filterVO.getPageNo());
        response.setPageSize(paginatedCommentList.size());

    }

    private SimplePostVO[] convertToSimplePostArrayForSorting(PostResponse[] postResponse) {
        SimplePostVO[] simplePostArray = new SimplePostVO[postResponse.length];

        for (int i = 0; i < postResponse.length; i++) {
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
