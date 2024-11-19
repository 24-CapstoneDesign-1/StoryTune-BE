package com.capstone.storytune.domain.book.controller;

import com.capstone.storytune.domain.book.dto.response.BooksResponse;
import com.capstone.storytune.domain.book.service.BookService;
import com.capstone.storytune.domain.user.annotation.CurrentUser;
import com.capstone.storytune.domain.user.domain.User;
import com.capstone.storytune.global.dto.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.web.bind.annotation.*;

import static com.capstone.storytune.global.dto.SuccessCode.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/book")
public class BookController {
    private final BookService bookService;

    // 전체 책 목록 조회
    @Operation(summary = "동화 목록", description = "기존 책 목록 조회 API (ex. 피노키오) (전체)")
    @GetMapping("")
    public BaseResponse<BooksResponse> getBooks(@CurrentUser User user){
        val result = bookService.getBooks();
        return BaseResponse.success(READ_BOOKS_SUCCESS, result);
    }

    // 동화 검색
    @Operation(summary = "동화 검색", description = "제목으로 검색한 책 목록 조회 API")
    @GetMapping("?{title}")
    public BaseResponse<BooksResponse> getBooksByTitle(@PathVariable String title){
        val result = bookService.getBooksByTitle(title);
        return BaseResponse.success(READ_SEARCHED_BOOKS_BY_TITLE_SUCCESS, result);
    }
}
