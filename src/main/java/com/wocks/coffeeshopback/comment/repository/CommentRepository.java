package com.wocks.coffeeshopback.comment.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.wocks.coffeeshopback.comment.domain.Comment;


public interface CommentRepository extends JpaRepository<Comment, Long> {
    Long countByPostId(Long postId);

    Page<Comment> findByPostIdAndIsDeletedFalse(Pageable pageable, Long postId);
}
