package com.capstone.storytune.domain.book.controller;

import com.capstone.storytune.domain.book.dto.response.BooksResponse;
import com.capstone.storytune.domain.book.dto.response.MyBooksResponse;
import com.capstone.storytune.domain.book.service.BookService;
import com.capstone.storytune.domain.user.annotation.CurrentUser;
import com.capstone.storytune.domain.user.domain.User;
import com.capstone.storytune.global.dto.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.capstone.storytune.global.dto.SuccessCode.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BookController {
    private final BookService bookService;

    // 전체 책 목록 조회
    @Operation(summary = "동화 목록", description = "기존 책 목록 조회 API (5개) (ex. 피노키오)")
    @GetMapping("/book")
    public BaseResponse<BooksResponse> getBooks(@CurrentUser User user){
        val result = bookService.getBooks();
        return BaseResponse.success(READ_BOOKS_SUCCESS, result);
    }

    // 내가 만든 책 목록 조회
    @Operation(summary = "내가 만든 책", description = "내가 만든 책 목록 조회 API (전체)")
    @GetMapping("/mybook")
    public BaseResponse<MyBooksResponse> getMyBooks(@CurrentUser User user){
        val result = bookService.getMyBooks(user);
        return BaseResponse.success(READ_MY_BOOKS_SUCCESS, result);
    }

    // 추천 책 목록 조회
    @Operation(summary = "다른 친구들은 어떤 책으로 만들었을까?", description = "추천 책 목록 조회 API (최신 5개)")
    @GetMapping("/book/recommendations")
    public BaseResponse<BooksResponse> getRecommendBooks(@CurrentUser User user){
        val result = bookService.getRecommendBooks(user);
        return BaseResponse.success(READ_RECOMMEND_BOOKS_SUCCESS, result);
    }
}
