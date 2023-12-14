package com.wocks.coffeeshopback.comment.domain;


import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.wocks.coffeeshopback.comment.dto.CommentUpdateRequestDto;
import com.wocks.coffeeshopback.common.entity.TimestampBase;
import com.wocks.coffeeshopback.post.domain.Post;
import com.wocks.coffeeshopback.user.domain.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment extends TimestampBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String content;

    @Column
    private boolean isDeleted;

    @Column
    private LocalDateTime deletedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Post post;


    public void update(CommentUpdateRequestDto dto) {
        content = dto.getContent();
    }

    public void delete() {
        isDeleted = true;
        deletedAt = LocalDateTime.now();
    }

}
