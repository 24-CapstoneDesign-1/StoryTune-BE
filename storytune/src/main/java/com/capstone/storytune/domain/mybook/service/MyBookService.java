package com.capstone.storytune.domain.mybook.service;

import com.capstone.storytune.domain.book.domain.Book;
import com.capstone.storytune.domain.book.dto.response.BookResponse;
import com.capstone.storytune.domain.book.dto.response.BooksResponse;
import com.capstone.storytune.domain.book.repository.BookRepository;
import com.capstone.storytune.domain.mybook.domain.MyBookCharacter;
import com.capstone.storytune.domain.mybook.domain.MyBookContent;
import com.capstone.storytune.domain.mybook.dto.request.*;
import com.capstone.storytune.domain.mybook.dto.response.*;
import com.capstone.storytune.domain.mybook.domain.MyBook;
import com.capstone.storytune.domain.mybook.exception.*;
import com.capstone.storytune.domain.mybook.repository.MyBookCharacterRepository;
import com.capstone.storytune.domain.mybook.repository.MyBookContentRepository;
import com.capstone.storytune.domain.mybook.repository.MyBookRepository;
import com.capstone.storytune.domain.user.domain.User;
import com.capstone.storytune.global.util.chatgpt.controller.ChatGPTController;
import com.capstone.storytune.global.util.chatgpt.service.ChatGPTService;
import com.capstone.storytune.global.util.s3.service.S3Service;
import com.capstone.storytune.global.util.stt.service.STTService;
import jakarta.transaction.Transactional;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static com.capstone.storytune.global.dto.ErrorCode.*;

@Service
@Builder
@RequiredArgsConstructor
@Transactional
public class MyBookService {
    private final MyBookRepository myBookRepository;
    private final BookRepository bookRepository;
    private final MyBookContentRepository myBookContentRepository;

    private final S3Service s3Service;
    private final ChatGPTController gptController;
    private final MyBookCharacterRepository myBookCharacterRepository;
    private final STTService sttService;
    private final ChatGPTController chatGPTController;

    public MyBooksResponse getMyBooks(User user){
        List<MyBookResponse> myBooks = myBookRepository.findByUserOrderByCreatedAtDesc(user)
                .stream()
                .map(MyBookResponse::of)
                .toList();

        return MyBooksResponse.of(myBooks);
    }

    public BooksResponse getRecommendBooks(User user){
        // 특정 사용자가 소유한 모든 Book ID 목록 가져오기
        List<Long> userBookIds = myBookRepository.findBookIdsByUser(user);

        // 특정 사용자가 소유하지 않은 최신 MyBook 목록 가져오기
        List<MyBook> recentMyBooks = myBookRepository.findByUserNotOrderByCreatedDateDesc(user);

        Set<Long> uniqueBookIds = new HashSet<>();
        List<BookResponse> recommendBooks = new ArrayList<>();

        for (MyBook myBook : recentMyBooks) {
            Long bookId = myBook.getId();
            if (!userBookIds.contains(bookId) && uniqueBookIds.add(bookId)) {
                recommendBooks.add(BookResponse.of(myBook.getBook()));
            }
            if (recommendBooks.size() == 5) break;
        }
        return BooksResponse.of(recommendBooks);
    }

    public MyBookCreateResponse createMyBook(MyBookCreateRequest request, User user){
        Book book = null;

        if(request.bookId() != null){
            book = bookRepository.findById(request.bookId())
                    .orElseThrow(() -> new NotFoundBookIdException(NOT_FOUND_BOOK_ID_EXCEPTION));
        }

        MyBook newMyBook = MyBook.builder()
                .book(book)
                .user(user)
                .build();

        myBookRepository.save(newMyBook);
        return MyBookCreateResponse.of(newMyBook);
    }

    public ImagesResponse updateImages(ImagesRequest request, Long myBookId){
        // 사진 리스트 추출
        List<MultipartFile> images = request.images();

        // myBook 찾기
        MyBook myBook = myBookRepository.findById(myBookId)
                .orElseThrow(() -> new NotFoundMyBookIdException(NOT_FOUND_MY_BOOK_ID_EXCEPTION));

        // 페이지 번호 카운팅
        AtomicInteger pageCounter = new AtomicInteger(1);

        // S3 업로드 및 MyBookContent 생성
        List<MyBookContent> myBookContents = images.stream()
                .map(photo -> {
                    String imageUrl;
                    try{
                        imageUrl = s3Service.uploadImage(photo);
                    } catch (IOException e){
                        throw new FailedUploadImageException(INTERNAL_SERVER_ERROR);
                    }

                    return MyBookContent.builder()
                            .image(imageUrl)
                            .page(pageCounter.getAndIncrement())
                            .myBook(myBook)
                            .build();
                })
                .map(myBookContentRepository::save)
                .toList();

        return ImagesResponse.of(myBookContents);
    }

    public void updateTopic(TopicRequest request, Long myBookId){
        MyBook mybook = myBookRepository.findById(myBookId)
                .orElseThrow(() -> new NotFoundMyBookIdException(NOT_FOUND_MY_BOOK_ID_EXCEPTION));

        mybook.updateTopic(request.topic());
        myBookRepository.save(mybook);
    }

    public MyBookContentResponse getMyBookContent(Long myBookId, int pageNum){
        // myBookId, pageNum으로 myBookContent 찾기
        MyBookContent myBookContent = myBookContentRepository.findByMyBook_IdAndPage(myBookId, pageNum)
                .orElseThrow( () -> new NotFoundMyBookContentException(NOT_FOUND_MY_BOOK_CONTENT_EXCEPTION));

        // 현재보다 이전 이야기 전달 - guide 질문 생성용
        String previousContent = getPreviousContentSummary(myBookId, pageNum);

        return MyBookContentResponse.of(myBookContent, previousContent);
    }

    private String getPreviousContentSummary(Long myBookId, int pageNum){
        List<MyBookContent> previousContents = myBookContentRepository.findAllByMyBook_IdAndPageLessThan(myBookId, pageNum);
        return previousContents.stream()
                .map(MyBookContent::getContent_story)
                .collect(Collectors.joining(" "));
    }

    public void updateStory(StoryRequest request, Long myBookContentId){
        // myBookContent 찾기
        MyBookContent myBookContent = myBookContentRepository.findById(myBookContentId)
                .orElseThrow(() -> new NotFoundMyBookContentException(NOT_FOUND_MY_BOOK_CONTENT_EXCEPTION));

        // myBookCharacter 찾기
        MyBookCharacter myBookCharacter = null;
        if(request.myBookCharacterId() != null){
            myBookCharacter = myBookCharacterRepository.findById(request.myBookCharacterId())
                    .orElseThrow(() -> new NotFoundMyBookCharacterException(NOT_FOUND_MY_BOOK_CHARACTER_EXCEPTION));
        }

        // stt
        String content = sttService.processStt(request.audio(), "Kor");

        // content -> 2가지 버전으로 formatting (gpt api)
        List<String> contents = new ArrayList<>();
        contents.add("다음은 현재 장면에 사용자가 입력한 이야기 내용입니다.\n" + content);
        contents.add("다음은 현재 장면에 입력된 내용의 해설/대사 여부입니다. (해설이면 false, 대사이면 true)\n" + request.isLine());
        if(myBookCharacter != null){
            contents.add("다음은 현재 장면에 입력된 내용의 대사를 말하는 등장인물의 이름입니다.\n" + myBookCharacter.getName());
        }

        String scenario = gptController.makeScenario(contents);
        String story = gptController.makeStory(contents);

        // 정보 update
        myBookContent.updateStory(content, scenario, story, request.isLine(), myBookCharacter);
        myBookContentRepository.save(myBookContent);
    }

    public void updateTitle(MultipartFile file, Long myBookId){
        //myBook 찾기
        MyBook myBook = myBookRepository.findById(myBookId)
                .orElseThrow(() -> new NotFoundMyBookIdException(NOT_FOUND_MY_BOOK_ID_EXCEPTION));

        // title update
        String title = sttService.processStt(file, "Kor");
        myBook.updateTitle(title);
        myBookRepository.save(myBook);
    }

    public List<CharacterResponse> getCharacter(Long myBookId){
        return myBookCharacterRepository.findByMyBook_Id(myBookId)
                .stream()
                .map(CharacterResponse::of)
                .toList();
    }

    public void updateCover(CoverRequest request, Long myBookId){
        //myBook 찾기
        MyBook myBook = myBookRepository.findById(myBookId)
                .orElseThrow(() -> new NotFoundMyBookIdException(NOT_FOUND_MY_BOOK_ID_EXCEPTION));

        //cover 설정
        MyBookContent myBookContent = myBookContentRepository.findById(request.myBookContentId())
                .orElseThrow(() -> new NotFoundMyBookContentException(NOT_FOUND_MY_BOOK_CONTENT_EXCEPTION));

        myBook.updateCover(myBookContent.getImage());
        myBookRepository.save(myBook);
    }

    public CharacterImagesResponse createCharacter(CharacterImagesRequest request, Long myBookId){
        // 등장인물 사진 리스트 추출
        List<MultipartFile> images = request.images();

        // myBook 찾기
        MyBook myBook = myBookRepository.findById(myBookId)
                .orElseThrow(() -> new NotFoundMyBookIdException(NOT_FOUND_MY_BOOK_ID_EXCEPTION));

        // S3 업로드 및 MyBookCharacter 생성
        List<MyBookCharacter> myBookCharacters = images.stream()
                .map(photo -> {
                    String imageUrl;
                    try{
                        imageUrl = s3Service.uploadImage(photo);
                    } catch (IOException e){
                        throw new FailedUploadImageException(INTERNAL_SERVER_ERROR);
                    }

                    return MyBookCharacter.builder()
                            .myBook(myBook)
                            .image(imageUrl)
                            .build();
                })
                .map(myBookCharacterRepository::save)
                .toList();

        // 해설 생성
        MyBookCharacter narration = MyBookCharacter.builder()
                .myBook(myBook)
                .image(null)
                .build();
        narration.updateName("해설");
        myBookCharacterRepository.save(narration);

        return CharacterImagesResponse.of(myBookCharacters);
    }

    public CharacterNameResponse updateCharacterName(MultipartFile file, Long myBookCharacterId){
        // myBookCharacter 찾기
        MyBookCharacter myBookCharacter = myBookCharacterRepository.findById(myBookCharacterId)
                .orElseThrow(() -> new NotFoundMyBookCharacterException(NOT_FOUND_MY_BOOK_CHARACTER_EXCEPTION));

        // 이름 설정
        String name = sttService.processStt(file, "Kor");
        myBookCharacter.updateName(name);
        myBookCharacterRepository.save(myBookCharacter);

        return CharacterNameResponse.of(myBookCharacter);
    }

    public void updateCompleted(Long myBookId){
        // myBook 찾기
        MyBook myBook = myBookRepository.findById(myBookId)
                .orElseThrow(() -> new NotFoundMyBookIdException(NOT_FOUND_MY_BOOK_ID_EXCEPTION));

        // completed 업데이트
        myBook.updateCompleted(true);
        myBookRepository.save(myBook);
    }

    public MyBookDetailsResponse getMyBookDetails(Long myBookId){
        // myBook 찾기
        MyBook myBook = myBookRepository.findById(myBookId)
                .orElseThrow(() -> new NotFoundMyBookIdException(NOT_FOUND_MY_BOOK_ID_EXCEPTION));

        // myBookContent 찾기
        List<MyBookDetailResponse> details = myBookContentRepository.findAllByMyBook(myBook)
                .stream()
                .map(MyBookDetailResponse::of)
                .toList();

        return MyBookDetailsResponse.of(myBook, details);
    }

    public void updateMyBookInEnglish(Long myBookId){
        // myBook 찾기
        MyBook myBook = myBookRepository.findById(myBookId)
                .orElseThrow(() -> new NotFoundMyBookIdException(NOT_FOUND_MY_BOOK_ID_EXCEPTION));

        // myBookContents 가져오기 및 변환
        List<MyBookContent> myBookContents = myBookContentRepository.findAllByMyBook(myBook);

        for(MyBookContent content : myBookContents){
            String translatedScenario = chatGPTController.makeInEnglish(content.getContent_scenario());
            String translatedStory = chatGPTController.makeInEnglish(content.getContent_story());

            content.updateInEnglish(translatedScenario, translatedStory);
        }
        myBookContentRepository.saveAll(myBookContents);
    } 
}
