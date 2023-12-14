package com.wocks.coffeeshopback.post.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//import com.wocks.coffeeshopback.common.annotation.CurrentUser;
import com.wocks.coffeeshopback.comment.dto.CommentPageDto;
import com.wocks.coffeeshopback.common.annotation.LoginRequired;
import com.wocks.coffeeshopback.common.response.ResponseBody;
import com.wocks.coffeeshopback.jwt.CurrentUser;
import com.wocks.coffeeshopback.post.domain.Post;
import com.wocks.coffeeshopback.post.domain.PostType;
import com.wocks.coffeeshopback.post.dto.PostAddRequestDto;
import com.wocks.coffeeshopback.post.dto.PostAddResponseDto;
import com.wocks.coffeeshopback.post.dto.PostDeleteRequestDto;
import com.wocks.coffeeshopback.post.dto.PostPageResponseDto;
import com.wocks.coffeeshopback.post.dto.PostReadResponseDto;
import com.wocks.coffeeshopback.post.dto.PostUpdateRequestDto;
import com.wocks.coffeeshopback.post.dto.PostUpdateResponseDto;
import com.wocks.coffeeshopback.post.service.PostService;
import com.wocks.coffeeshopback.user.domain.User;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("/")
    public ResponseEntity<ResponseBody<?>> add(
        @RequestBody @Valid PostAddRequestDto dto,
        @CurrentUser User user
    ) {
        PostAddResponseDto responseDto = postService.save(user, dto);

        ResponseBody<PostAddResponseDto> responseBody = ResponseBody.<PostAddResponseDto>builder()
            .status(HttpStatus.CREATED)
            .message("게시물이 생성되었습니다.")
            .data(responseDto)
            .build();
        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }


    @GetMapping("/{id}/")
    public ResponseEntity<ResponseBody<?>> getPostById(@PathVariable("id") Long id) {
        PostReadResponseDto responseDto = postService.getPost(id);
        ResponseBody<PostReadResponseDto> responseBody = ResponseBody.<PostReadResponseDto>builder()
            .status(HttpStatus.OK)
            .message("게시물을 가져왔습니다.")
            .data(responseDto)
            .build();
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }


    @GetMapping("/{id}/comments")
    public ResponseEntity<ResponseBody<?>> getCommentsById(
        @PathVariable("id") Long id,
        @PageableDefault(page = 0, size=10) Pageable pageable
    ) {
        CommentPageDto responseDto = postService.getComments(id, pageable);
        ResponseBody<CommentPageDto> responseBody = ResponseBody.<CommentPageDto>builder()
            .status(HttpStatus.OK)
            .message("댓글을 가져왔습니다.")
            .data(responseDto)
            .build();
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }


    @GetMapping("/test/")
    public String test(@CurrentUser User user) {
        return "test..." + user.getUserName() + " " + user.getId();
    }


    @GetMapping("/community/")
    public ResponseEntity<ResponseBody<?>> getCommunityPosts(
        @PageableDefault(page = 0, size=10) Pageable pageable
    ) {
        Page<PostPageResponseDto> responseDto = postService.getPosts(pageable, PostType.Community);
        ResponseBody<Page<?>> responseBody = ResponseBody.<Page<?>>builder()
            .status(HttpStatus.OK)
            .message("게시물을 가져왔습니다.")
            .data(responseDto)
            .build();
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }


    @GetMapping("/qna/")
    public ResponseEntity<ResponseBody<?>> getQnaPosts(
        @PageableDefault(page = 0) Pageable pageable
    ) {
        Page<PostPageResponseDto> responseDto = postService.getPosts(pageable, PostType.QNA);
        ResponseBody<Page<?>> responseBody = ResponseBody.<Page<?>>builder()
            .status(HttpStatus.OK)
            .message("게시물을 가져왔습니다.")
            .data(responseDto)
            .build();
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }


    @GetMapping("/notice/")
    public ResponseEntity<ResponseBody<?>> getNoticePosts(
        @PageableDefault(page = 0) Pageable pageable
    ) {
        Page<PostPageResponseDto> responseDto = postService.getPosts(pageable, PostType.Notice);
        ResponseBody<Page<?>> responseBody = ResponseBody.<Page<?>>builder()
            .status(HttpStatus.OK)
            .message("게시물을 가져왔습니다.")
            .data(responseDto)
            .build();
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }


    @GetMapping("/tech/")
    public ResponseEntity<ResponseBody<?>> getTechPosts(
        @PageableDefault(page = 0) Pageable pageable
    ) {
        Page<PostPageResponseDto> responseDto = postService.getPosts(pageable, PostType.Tech);
        ResponseBody<Page<?>> responseBody = ResponseBody.<Page<?>>builder()
            .status(HttpStatus.OK)
            .message("게시물을 가져왔습니다.")
            .data(responseDto)
            .build();
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }


    @PutMapping("/")
    public ResponseEntity<ResponseBody<?>> updatePost(
        @RequestBody @Valid PostUpdateRequestDto dto,
        @CurrentUser User user
    ) {
        PostUpdateResponseDto responseDto = postService.update(user, dto);

        ResponseBody<PostUpdateResponseDto> responseBody = ResponseBody.<PostUpdateResponseDto>builder()
            .status(HttpStatus.OK)
            .message("게시물을 수정했습니다.")
            .data(responseDto)
            .build();
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }


    @DeleteMapping("/")
    public ResponseEntity<ResponseBody<?>> deletePost(
        @RequestBody @Valid PostDeleteRequestDto dto,
        @CurrentUser User user
    ) {
        postService.delete(user, dto);

        ResponseBody<String> responseBody = ResponseBody.<String>builder()
            .status(HttpStatus.OK)
            .message("게시물을 삭제했습니다.")
            .build();
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

}
