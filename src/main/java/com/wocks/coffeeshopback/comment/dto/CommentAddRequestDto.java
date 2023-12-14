package com.wocks.coffeeshopback.comment.dto;

import javax.validation.constraints.NotNull;

import lombok.Getter;


@Getter
public class CommentAddRequestDto {

    @NotNull(message = "게시물 아이디를 입력해주세요.")
    private Long postId;

    @NotNull(message = "댓글의 내용을 입력해주세요.")
    private String content;
}
