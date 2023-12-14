package com.wocks.coffeeshopback.comment.dto;


import com.wocks.coffeeshopback.comment.domain.Comment;
import com.wocks.coffeeshopback.post.domain.Post;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentAddResponseDto {
    final private Long id;
    final private Long postId;
    final private String userName;
    final private String content;

    @Builder
    public CommentAddResponseDto(Comment comment, Long postId, String userName) {
        id = comment.getId();
        content = comment.getContent();
        this.postId = postId;
        this.userName = userName;
    }
}
