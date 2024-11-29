package com.capstone.storytune.domain.mybook.controller;

import com.capstone.storytune.domain.book.dto.response.BooksResponse;
import com.capstone.storytune.domain.mybook.dto.request.*;
import com.capstone.storytune.domain.mybook.dto.response.*;
import com.capstone.storytune.domain.mybook.service.MyBookService;
import com.capstone.storytune.domain.user.annotation.CurrentUser;
import com.capstone.storytune.domain.user.domain.User;
import com.capstone.storytune.global.dto.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
    @PostMapping("/mybook/{myBookId}/images")
    public BaseResponse<ImagesResponse> updateImages(@ModelAttribute ImagesRequest request, @PathVariable Long myBookId){
        val result = myBookService.updateImages(request, myBookId);
        return BaseResponse.success(UPDATE_MY_BOOK_IMAGES_SUCCESS, result);
    }

    // 동화 만들기 - 주제 고르기
    @Operation(summary = "새로운 이야기는 어떤 내용인가요?", description = "나만의 동화 만들기 주제 설정 API")
    @PatchMapping("/mybook/{myBookId}/topic")
    public BaseResponse updateTopic(@RequestBody TopicRequest request, @PathVariable Long myBookId){
        myBookService.updateTopic(request, myBookId);
        return BaseResponse.success(UPDATE_MY_BOOK_TOPIC_SUCCESS);
    }

    // 등장인물 사진 등록
    @Operation(summary = "STORY TUNE이 분석한 이야기 주인공이에요!", description = "등장인물 사진 등록하는 API")
    @PostMapping("/mybook/{myBookId}/character")
    public BaseResponse<CharacterImagesResponse> createCharacter(@ModelAttribute CharacterImagesRequest request, @PathVariable Long myBookId){
        val result = myBookService.createCharacter(request, myBookId);
        return BaseResponse.success(CREATE_MY_BOOK_CHARACTER_SUCCESS, result);
    }

    // 등장인물 이름 등록
    @Operation(summary = "이 친구의 이름은 무엇인가요?", description = "등장인물 이름 등록하는 API")
    @PatchMapping("/mybook/character/{myBookCharacterId}")
    public BaseResponse updateCharacterName(@RequestParam("file")MultipartFile file, @PathVariable Long myBookCharacterId){
        myBookService.updateCharacterName(file, myBookCharacterId);
        return BaseResponse.success(UPDATE_MY_BOOK_CHARACTER_SUCCESS);
    }

    // 등장인물 조회
    @Operation(summary = "누구의 대사인가요?", description = "나만의 동화 만들기 등장인물 조회 API")
    @GetMapping("/mybook/{myBookId}/character")
    public BaseResponse<List<CharacterResponse>> getCharacters(@PathVariable Long myBookId){
        val result = myBookService.getCharacter(myBookId);
        return BaseResponse.success(READ_MY_BOOK_CHARACTERS_SUCCESS, result);

    }

    // 동화 만들기 - 상세 페이지 조회
    @Operation(summary = "이 사진을 보고 떠오르는 이야기를 들려주세요!", description = "나만의 동화 만들기 상세 페이지 조회 API")
    @GetMapping("/mybook/{myBookId}/{pageNum}")
    public BaseResponse<MyBookContentResponse> getMyBookDetail(@PathVariable Long myBookId, @PathVariable int pageNum){
        val result = myBookService.getMyBookContent(myBookId, pageNum);
        return BaseResponse.success(READ_MY_BOOK_CONTENT_SUCCESS, result);
    }

    // 동화 만들기 - 상세 페이지 이야기 생성 (이야기 저장 -> 포맷팅)
    @Operation(summary = "이 사진을 보고 떠오르는 이야기를 들려주세요!", description = "나만의 동화 만들기 상세 페이지 이야기 생성 API")
    @PatchMapping("/mybook/{myBookContentId}")
    public BaseResponse updateStory(@ModelAttribute StoryRequest request, @PathVariable Long myBookContentId){
        myBookService.updateStory(request, myBookContentId);
        return BaseResponse.success(UPDATE_MY_BOOK_CONTENT_SUCCESS);
    }

    // 동화 만들기 - 제목 설정하기
    @Operation(summary = "이 동화의 제목은 무엇인가요?", description = "나만의 동화 만들기 제목 설정 API")
    @PatchMapping("/mybook/{myBookId}/title")
    public BaseResponse updateTitle(@RequestParam("file")MultipartFile file, @PathVariable Long myBookId){
        myBookService.updateTitle(file, myBookId);
        return BaseResponse.success(UPDATE_MY_BOOK_TITLE_SUCCESS);
    }

    // 표지 설정
    @Operation(summary = "이 동화책의 표지를 선택해 주세요!", description = "나만의 동화 만들기 표지 선택 API")
    @PatchMapping("/mybook/{myBookId}/cover")
    public BaseResponse updateCover(@RequestBody CoverRequest request, @PathVariable Long myBookId){
        myBookService.updateCover(request, myBookId);
        return BaseResponse.success(UPDATE_MY_BOOK_COVER_SUCCESS);
    }

    // 끝까지 만든거 확인 -> boolean 바꾸기
    @Operation(summary = "나만의 동화가 완성되었어요!", description = "나만의 동화 만들기 종료 시 임시저장 / 최종본 여부를 체크하기 위한 API")
    @PatchMapping("/mybook/{myBookId}/completed")
    public BaseResponse updateCompleted(@PathVariable Long myBookId){
        myBookService.updateCompleted(myBookId);
        return BaseResponse.success(UPDATE_MY_BOOK_COMPLETED_SUCCESS);
    }

    // 내가 만든 동화 보러가기

    // 영어 동화로 바꾸고 싶어요 -> 영어로 바꿔서 저장

}
