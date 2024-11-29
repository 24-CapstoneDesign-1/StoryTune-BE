package com.capstone.storytune.domain.mybook.domain;

import com.capstone.storytune.domain.book.domain.Book;
import com.capstone.storytune.domain.user.domain.User;
import com.capstone.storytune.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class MyBook extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mybook_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookId")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    private String title;

    private String cover;

    private Boolean completed;

    @Enumerated(EnumType.STRING)
    private Topic topic;

    public MyBook(User user){
        this.user = user;
        this.completed = false;
    }

    @Builder
    public MyBook(User user, Book book){
        this.user = user;
        this.book = book;
        this.completed = false;
    }

    public void updateTopic(Topic topic){
        this.topic = topic;
    }

    public void updateTitle(String title){
        this.title = title;
    }

    public void updateCover(String cover){
        this.cover = cover;
    }

    public void updateCompleted(Boolean completed){
        this.completed = completed;
    }

}
