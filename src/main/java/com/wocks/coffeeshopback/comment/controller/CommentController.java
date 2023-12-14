package com.wocks.coffeeshopback.comment.controller;


import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wocks.coffeeshopback.comment.dto.CommentAddRequestDto;
import com.wocks.coffeeshopback.comment.dto.CommentAddResponseDto;
import com.wocks.coffeeshopback.comment.dto.CommentDeleteRequestDto;
import com.wocks.coffeeshopback.comment.dto.CommentUpdateRequestDto;
import com.wocks.coffeeshopback.comment.dto.CommentUpdateResponseDto;
import com.wocks.coffeeshopback.comment.service.CommentService;
import com.wocks.coffeeshopback.common.response.ResponseBody;
import com.wocks.coffeeshopback.jwt.CurrentUser;
import com.wocks.coffeeshopback.user.domain.User;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/")
    public ResponseEntity<ResponseBody<?>> addComment(
        @RequestBody @Valid CommentAddRequestDto dto,
        @CurrentUser User user
    ) {
        CommentAddResponseDto responseDto = commentService.addComment(user, dto);

        ResponseBody<CommentAddResponseDto> responseBody = ResponseBody.<CommentAddResponseDto>builder()
            .status(HttpStatus.CREATED)
            .message("댓글을 작성했습니다.")
            .data(responseDto)
            .build();
        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }


    @PutMapping("/")
    public ResponseEntity<ResponseBody<?>> updateComment(
        @RequestBody @Valid CommentUpdateRequestDto dto,
        @CurrentUser User user
    ) {
        CommentUpdateResponseDto responseDto = commentService.updateComment(user, dto);

        ResponseBody<CommentUpdateResponseDto> responseBody = ResponseBody.<CommentUpdateResponseDto>builder()
            .status(HttpStatus.OK)
            .message("댓글을 수정했습니다.")
            .data(responseDto)
            .build();
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }


    @DeleteMapping("/")
    public ResponseEntity<ResponseBody<?>> deleteComment(
        @RequestBody @Valid CommentDeleteRequestDto dto,
        @CurrentUser User user
    ) {
        commentService.deleteComment(user, dto);

        ResponseBody<CommentUpdateResponseDto> responseBody = ResponseBody.<CommentUpdateResponseDto>builder()
            .status(HttpStatus.OK)
            .message("댓글을 삭제했습니다.")
            .build();
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

}
