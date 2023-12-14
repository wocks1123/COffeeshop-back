package com.wocks.coffeeshopback.post.dto;


import java.time.LocalDateTime;

import com.wocks.coffeeshopback.post.domain.Post;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostPageResponseDto {
    private Long id;
    private String title;
    private Long view;
    private String userName;
    private Long commentCount;
    private LocalDateTime createdAt;

    @Builder
    public PostPageResponseDto(Post post, Long commentCount) {
        id = post.getId();
        title = post.getTitle();
        view = post.getView();
        createdAt = post.getCreatedAt();
        this.commentCount = commentCount;
        userName = post.getUser().getUserName();
    }

}
