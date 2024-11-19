package com.capstone.storytune.domain.mybook.service;

import com.capstone.storytune.domain.book.domain.Book;
import com.capstone.storytune.domain.book.dto.response.BookResponse;
import com.capstone.storytune.domain.book.dto.response.BooksResponse;
import com.capstone.storytune.domain.book.repository.BookRepository;
import com.capstone.storytune.domain.mybook.domain.MyBookContent;
import com.capstone.storytune.domain.mybook.dto.request.ImagesRequest;
import com.capstone.storytune.domain.mybook.dto.request.MyBookCreateRequest;
import com.capstone.storytune.domain.mybook.dto.request.TopicRequest;
import com.capstone.storytune.domain.mybook.dto.response.ImagesResponse;
import com.capstone.storytune.domain.mybook.dto.response.MyBookCreateResponse;
import com.capstone.storytune.domain.mybook.dto.response.MyBookResponse;
import com.capstone.storytune.domain.mybook.dto.response.MyBooksResponse;
import com.capstone.storytune.domain.mybook.domain.MyBook;
import com.capstone.storytune.domain.mybook.exception.FailedUploadImageException;
import com.capstone.storytune.domain.mybook.exception.NotFoundBookIdException;
import com.capstone.storytune.domain.mybook.exception.NotFoundMyBookIdException;
import com.capstone.storytune.domain.mybook.repository.MyBookContentRepository;
import com.capstone.storytune.domain.mybook.repository.MyBookRepository;
import com.capstone.storytune.domain.user.domain.User;
import com.capstone.storytune.global.util.s3.service.S3Service;
import jakarta.transaction.Transactional;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static com.capstone.storytune.global.dto.ErrorCode.*;

@Service
@Builder
@RequiredArgsConstructor
@Transactional
public class MyBookService {
    private final MyBookRepository myBookRepository;
    private final BookRepository bookRepository;
    private final S3Service s3Service;
    private final MyBookContentRepository myBookContentRepository;

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

    public ImagesResponse updateImages(ImagesRequest request, User user){
        // 사진 리스트 추출
        List<MultipartFile> images = request.images();

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
}
