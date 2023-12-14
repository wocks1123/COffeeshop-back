package com.wocks.coffeeshopback.post.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.wocks.coffeeshopback.post.domain.Post;
import com.wocks.coffeeshopback.post.domain.PostType;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findByIsDeletedFalseAndType(Pageable pageable, PostType type);
}
