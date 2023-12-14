package com.wocks.coffeeshopback.comment.dto;


import com.wocks.coffeeshopback.comment.domain.Comment;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentUpdateResponseDto {
    private Long id;
    private String content;
    private String userName;

    @Builder
    public CommentUpdateResponseDto(Comment comment, String userName) {
        id = comment.getId();
        content = comment.getContent();
        this.userName = userName;
    }
}
