package com.capstone.storytune.domain.mybook.controller;

import com.capstone.storytune.domain.book.dto.response.BooksResponse;
import com.capstone.storytune.domain.mybook.dto.request.ImagesRequest;
import com.capstone.storytune.domain.mybook.dto.request.MyBookCreateRequest;
import com.capstone.storytune.domain.mybook.dto.request.StoryRequest;
import com.capstone.storytune.domain.mybook.dto.request.TopicRequest;
import com.capstone.storytune.domain.mybook.dto.response.ImagesResponse;
import com.capstone.storytune.domain.mybook.dto.response.MyBookContentResponse;
import com.capstone.storytune.domain.mybook.dto.response.MyBookCreateResponse;
import com.capstone.storytune.domain.mybook.dto.response.MyBooksResponse;
import com.capstone.storytune.domain.mybook.service.MyBookService;
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
@RequestMapping("/api")
public class MyBookController {
    private final MyBookService myBookService;

    // 내가 만든 책 목록 조회
    @Operation(summary = "내가 만든 책", description = "내가 만든 책 목록 조회 API (전체)")
    @GetMapping("/mybook")
    public BaseResponse<MyBooksResponse> getMyBooks(@CurrentUser User user){
        val result = myBookService.getMyBooks(user);
        return BaseResponse.success(READ_MY_BOOKS_SUCCESS, result);
    }

    // 추천 책 목록 조회
    @Operation(summary = "다른 친구들은 어떤 책으로 만들었을까?", description = "추천 책 목록 조회 API (최신 5개)")
    @GetMapping("/book/recommendations")
    public BaseResponse<BooksResponse> getRecommendBooks(@CurrentUser User user){
        val result = myBookService.getRecommendBooks(user);
        return BaseResponse.success(READ_RECOMMEND_BOOKS_SUCCESS, result);
    }

    // 동화 만들기 - 선택
    @Operation(summary = "동화책을 선택해 주세요!", description = "나만의 동화 만들기 할 책 설정 API (새로 만들기의 경우 null 값 전송)")
    @PostMapping("/mybook")
    public BaseResponse<MyBookCreateResponse> createMyBook(@RequestBody MyBookCreateRequest request, @CurrentUser User user){
        val result = myBookService.createMyBook(request, user);
        return BaseResponse.success(CREATE_MY_BOOK_SUCCESS, result);
    }

    // 동화 만들기 - 사진 업로드
    @Operation(summary = "사진을 골라주세요!", description = "사진 업로드 API (페이지 순서대로)")
    @PostMapping("/mybook/images")
    public BaseResponse<ImagesResponse> updateImages(@ModelAttribute ImagesRequest request, @CurrentUser User user){
        val result = myBookService.updateImages(request, user);
        return BaseResponse.success(UPDATE_MY_BOOK_IMAGES_SUCCESS, result);
    }

    // 동화 만들기 - 주제 고르기
    @Operation(summary = "새로운 이야기는 어떤 내용인가요?", description = "나만의 동화 만들기 주제 설정 API")
    @PatchMapping("/mybook/{myBookId}/topic")
    public BaseResponse updateTopic(@RequestBody TopicRequest request, @PathVariable Long myBookId){
        myBookService.updateTopic(request, myBookId);
        return BaseResponse.success(UPDATE_MY_BOOK_TOPIC_SUCCESS);
    }

    // 동화 만들기 - 상세 페이지 조회 (guide 질문 생성)
    @Operation(summary = "이 사진을 보고 떠오르는 이야기를 들려주세요!", description = "나만의 동화 만들기 상세 페이지 조회 API")
    @GetMapping("/mybook/{myBookId}/{pageNum}")
    public BaseResponse<MyBookContentResponse> getMyBookDetail(@PathVariable Long myBookId, @PathVariable int pageNum){
        val result = myBookService.getMyBookContent(myBookId, pageNum);
        return BaseResponse.success(READ_MY_BOOK_CONTENT_SUCCESS, result);
    }

    // 동화 만들기 - 상세 페이지 이야기 생성 (이야기 저장 -> 포맷팅)
    @Operation(summary = "이 사진을 보고 떠오르는 이야기를 들려주세요!", description = "나만의 동화 만들기 상세 페이지 이야기 생성 API")
    @PatchMapping("/mybook/{myBookContentId}")
    public BaseResponse updateStory(@RequestBody StoryRequest request, @PathVariable Long myBookContentId){
        myBookService.updateStory(request, myBookContentId);
        return BaseResponse.success(UPDATE_MY_BOOK_CONTENT_SUCCESS);
    }

    // 동화 만들기 - 제목 설정하기

    // 내가 만든 동화 읽어보기 (영어 동화로 만들기)

    // 내가 만든 동화 읽어보기 (버전 2가지 return)

}
