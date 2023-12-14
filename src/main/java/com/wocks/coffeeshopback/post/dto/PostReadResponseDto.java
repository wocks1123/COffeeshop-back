package com.wocks.coffeeshopback.post.dto;


import java.time.LocalDateTime;

import com.wocks.coffeeshopback.comment.dto.CommentPageDto;
import com.wocks.coffeeshopback.post.domain.Post;

import lombok.Builder;
import lombok.Getter;

@Getter

public class PostReadResponseDto {

    private final Long id;
    private final String title;
    private final String content;
    private final Long view;
    private final String userName;
    private final String type;
    private final LocalDateTime createdAt;
    private final CommentPageDto commentPage;

    @Builder
    public PostReadResponseDto(Post post, CommentPageDto commentPage) {
        id = post.getId();
        title = post.getTitle();
        content = post.getContent();
        view = post.getView();
        type = post.getType().name();
        createdAt = post.getCreatedAt();
        userName = post.getUser().getUserName();
        this.commentPage = commentPage;
    }

}
