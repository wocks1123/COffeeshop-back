package com.wocks.coffeeshopback.post.dto;

import com.wocks.coffeeshopback.post.domain.Post;

import lombok.Builder;
import lombok.Getter;


@Getter
public class PostUpdateResponseDto {

    final private Long id;
    final private String title;
    final private String content;
    final private Long view;
    final private String userName;
    final private String type;

    @Builder
    public PostUpdateResponseDto(Post post, String userName) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.view = post.getView();
        this.type = post.getType().name();
        this.userName = userName;
    }
}
