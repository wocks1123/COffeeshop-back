package com.wocks.coffeeshopback.post.dto;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.wocks.coffeeshopback.common.annotation.EnumValid;
import com.wocks.coffeeshopback.post.domain.PostType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostAddRequestDto {
    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    @NotBlank(message = "내용을 입력해주세요.")
    private String content;
    
    @EnumValid(enumClass = PostType.class, ignoreCase = true)
    @NotNull(message = "게시물 타입을 설정해주세요.")
    private String type;
}
