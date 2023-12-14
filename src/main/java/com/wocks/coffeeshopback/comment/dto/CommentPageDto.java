package com.wocks.coffeeshopback.comment.dto;


import java.util.List;

import org.springframework.data.domain.Page;

import com.wocks.coffeeshopback.comment.domain.Comment;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentPageDto {
    private long totalElements;
    private int totalPages;
    private int number;
    private int size;
    private int numberOfElements;
    private List<CommentResponseDto> content;

    @Builder
    public CommentPageDto(Page<Comment> commentPage, List<CommentResponseDto> comments) {
        this.content = comments;
        totalElements = commentPage.getTotalElements(); // 전체 아이템 갯수
        totalPages = commentPage.getTotalPages(); // 전체 페이지 크기
        number = commentPage.getNumber(); // 현재 페이지 넘버
        size = commentPage.getSize();  // 페이지당 아이템 갯수
        numberOfElements = commentPage.getNumberOfElements(); // 현재 패이지의 아이템 갯수
    }

}
