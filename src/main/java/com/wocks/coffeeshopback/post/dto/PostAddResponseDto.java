package com.wocks.coffeeshopback.post.dto;


import com.wocks.coffeeshopback.post.domain.Post;

import lombok.Builder;
import lombok.Getter;


@Getter
public class PostAddResponseDto {
    final private Long id;
    final private String title;
    final private String content;
    final private Long view;
    final private String userName;
    final private String type;

    @Builder
    public PostAddResponseDto(Post post, String userName) {
        id = post.getId();
        title = post.getTitle();
        content = post.getContent();
        view = post.getView();
        type = post.getType().name();
        this.userName = userName;
    }
}
