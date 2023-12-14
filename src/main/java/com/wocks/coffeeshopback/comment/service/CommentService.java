package com.wocks.coffeeshopback.comment.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wocks.coffeeshopback.comment.domain.Comment;
import com.wocks.coffeeshopback.comment.dto.CommentAddRequestDto;
import com.wocks.coffeeshopback.comment.dto.CommentAddResponseDto;
import com.wocks.coffeeshopback.comment.dto.CommentDeleteRequestDto;
import com.wocks.coffeeshopback.comment.dto.CommentUpdateRequestDto;
import com.wocks.coffeeshopback.comment.dto.CommentUpdateResponseDto;
import com.wocks.coffeeshopback.comment.exception.CommentException;
import com.wocks.coffeeshopback.comment.repository.CommentRepository;
import com.wocks.coffeeshopback.common.exception.RestApiException;
import com.wocks.coffeeshopback.post.domain.Post;
import com.wocks.coffeeshopback.post.exception.PostException;
import com.wocks.coffeeshopback.post.repository.PostRepository;
import com.wocks.coffeeshopback.post.service.PostService;
import com.wocks.coffeeshopback.user.domain.User;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional
    public CommentAddResponseDto addComment(User user, CommentAddRequestDto dto) {
        Post post = postRepository.findById(dto.getPostId())
            .orElseThrow(() -> new RestApiException(PostException.POST_NOT_FOUND));

        Comment comment = Comment.builder()
            .post(post)
            .user(user)
            .content(dto.getContent())
            .build();

        commentRepository.save(comment);

        return CommentAddResponseDto.builder()
            .postId(dto.getPostId())
            .userName(user.getUserName())
            .comment(comment)
            .build();
    }


    @Transactional
    public CommentUpdateResponseDto updateComment(User user, CommentUpdateRequestDto dto) {
        Comment comment = commentRepository.findById(dto.getId())
            .orElseThrow(() -> new RestApiException(CommentException.COMMENT_NOT_FOUND));

        if (!user.getUserName().equals(comment.getUser().getUserName())) {
            throw new RestApiException(CommentException.COMMENT_NOT_FOUND);
        }

        comment.update(dto);

        return CommentUpdateResponseDto.builder()
            .comment(comment)
            .userName(user.getUserName())
            .build();

    }


    @Transactional
    public void deleteComment(User user, CommentDeleteRequestDto dto) {
        Comment comment = commentRepository.findById(dto.getId())
            .orElseThrow(() -> new RestApiException(CommentException.COMMENT_NOT_FOUND));

        if (!user.getUserName().equals(comment.getUser().getUserName())) {
            throw new RestApiException(CommentException.COMMENT_NOT_FOUND);
        }

        comment.delete();
    }

}
