package com.wocks.coffeeshopback.post.domain;


import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.ColumnDefault;

import com.wocks.coffeeshopback.comment.domain.Comment;
import com.wocks.coffeeshopback.common.entity.TimestampBase;
import com.wocks.coffeeshopback.post.dto.PostUpdateRequestDto;
import com.wocks.coffeeshopback.user.domain.User;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Post extends TimestampBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    @ColumnDefault("0")
    private Long view = 0L;

    @Column
    private boolean isDeleted;

    @Column
    private LocalDateTime deletedAt;

    @Column
    @Enumerated(EnumType.STRING)
    private PostType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

    public Post update(PostUpdateRequestDto dto) {
        if (dto.getTitle() != null) title = dto.getTitle();
        if (dto.getContent() != null) content = dto.getContent();

        return this;
    }

    public void delete() {
        isDeleted = true;
        deletedAt = LocalDateTime.now();
    }

    public void addView() {
        view++;
    }

}
