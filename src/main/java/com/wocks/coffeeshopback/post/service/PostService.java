package com.wocks.coffeeshopback.post.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wocks.coffeeshopback.comment.domain.Comment;
import com.wocks.coffeeshopback.comment.dto.CommentPageDto;
import com.wocks.coffeeshopback.comment.dto.CommentResponseDto;
import com.wocks.coffeeshopback.comment.repository.CommentRepository;
import com.wocks.coffeeshopback.common.exception.RestApiException;
import com.wocks.coffeeshopback.post.domain.Post;
import com.wocks.coffeeshopback.post.domain.PostType;
import com.wocks.coffeeshopback.post.dto.PostAddRequestDto;
import com.wocks.coffeeshopback.post.dto.PostAddResponseDto;
import com.wocks.coffeeshopback.post.dto.PostDeleteRequestDto;
import com.wocks.coffeeshopback.post.dto.PostPageResponseDto;
import com.wocks.coffeeshopback.post.dto.PostReadResponseDto;
import com.wocks.coffeeshopback.post.dto.PostUpdateRequestDto;
import com.wocks.coffeeshopback.post.dto.PostUpdateResponseDto;
import com.wocks.coffeeshopback.post.exception.PostException;
import com.wocks.coffeeshopback.post.repository.PostRepository;
import com.wocks.coffeeshopback.user.domain.User;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;


    @Transactional
    public PostAddResponseDto save(User user, PostAddRequestDto dto) {
        Post post = Post.builder()
            .title(dto.getTitle())
            .content(dto.getContent())
            .type(PostType.valueOf(dto.getType()))
            .user(user)
            .view(0L)
            .build();

        postRepository.save(post);

        return PostAddResponseDto.builder()
            .post(post)
            .userName(user.getUserName())
            .build();
    }


    @Transactional
    public PostReadResponseDto getPost(Long id) {
        Post post = postRepository.findById(id)
            .orElseThrow(() -> new RestApiException(PostException.POST_NOT_FOUND));

        post.addView();

        Long count = commentRepository.countByPostId(post.getId());
        Page<Comment> commentPage = commentRepository.findByPostIdAndIsDeletedFalse(
            PageRequest.of(count.intValue() / 10, 10), post.getId()
        );

        List<CommentResponseDto> comments = commentPage.stream().map(
            CommentResponseDto::new
        ).toList();

        return PostReadResponseDto.builder()
            .post(post)
            .commentPage(
                CommentPageDto.builder().commentPage(commentPage).comments(comments).build())
            .build();
    }


    @Transactional
    public CommentPageDto getComments(Long postId, Pageable pageable) {
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new RestApiException(PostException.POST_NOT_FOUND));

        int page = pageable.getPageNumber();
        int pageLimit = pageable.getPageSize();

        Page<Comment> commentPage = commentRepository.findByPostIdAndIsDeletedFalse(
            PageRequest.of(page, pageLimit), post.getId()
        );

        List<CommentResponseDto> comments = commentPage.stream().map(
            CommentResponseDto::new
        ).toList();

        return CommentPageDto.builder()
            .commentPage(commentPage)
            .comments(comments)
            .build();
    }


    @Transactional
    public Page<PostPageResponseDto> getPosts(Pageable pageable, PostType type) {
        int page = pageable.getPageNumber();
        int pageLimit = pageable.getPageSize();

        Page<Post> postPage = postRepository.findByIsDeletedFalseAndType(
            PageRequest.of(page, pageLimit, Sort.by(Direction.DESC, "id")),
            type
        );

        Page<PostPageResponseDto> responseDtos = postPage.map(
            post -> new PostPageResponseDto(post, commentRepository.countByPostId(post.getId())));

        return responseDtos;
    }


    @Transactional
    public PostUpdateResponseDto update(User user, PostUpdateRequestDto dto) {
        Post post = postRepository.findById(dto.getId())
            .orElseThrow(() -> new RestApiException(PostException.POST_NOT_FOUND));

        if (!user.getId().equals(post.getUser().getId())) {
            throw new RestApiException(PostException.POST_BAD_REQUEST);
        }

        post.update(dto);

        return PostUpdateResponseDto.builder()
            .post(post)
            .userName(user.getUserName())
            .build();
    }


    @Transactional
    public void delete(User user, PostDeleteRequestDto dto) {
        Post post = postRepository.findById(dto.getId())
            .orElseThrow(() -> new RestApiException(PostException.POST_NOT_FOUND));

        if (!user.getId().equals(post.getUser().getId())) {
            throw new RestApiException(PostException.POST_BAD_REQUEST);
        }

        post.delete();
    }

}
