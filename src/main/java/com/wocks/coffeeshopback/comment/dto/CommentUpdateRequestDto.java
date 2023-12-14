package com.wocks.coffeeshopback.comment.dto;


import javax.validation.constraints.NotNull;

import lombok.Getter;


@Getter
public class CommentUpdateRequestDto {
    @NotNull(message = "아이디를 입력해주세요.")
    private Long id;

    @NotNull(message = "내용을 입력해주세요.")
    private String content;
}
