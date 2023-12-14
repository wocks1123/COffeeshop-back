package com.wocks.coffeeshopback.comment.dto;


import java.time.LocalDateTime;

import com.wocks.coffeeshopback.comment.domain.Comment;

import lombok.Getter;

@Getter
public class CommentResponseDto {
    private String userName;
    private String content;
    private LocalDateTime createdAt;

    public CommentResponseDto(Comment comment) {
        content = comment.getContent();
        createdAt = comment.getCreatedAt();
        userName = comment.getUser().getUserName();
    }
}
