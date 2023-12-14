package com.wocks.coffeeshopback.post.dto;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostDeleteRequestDto {
    @NotNull(message = "아이디를 입력해주세요.")
    private Long id;
}
