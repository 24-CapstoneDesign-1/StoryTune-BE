package com.capstone.storytune.domain.mybook.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class MyBookContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "myBookContent_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "myBookId")
    private MyBook myBook;

    private int page;

    private String image;

    private String guide;

    private boolean isLine;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(columnDefinition = "TEXT")
    private String content_scenario;

    @Column(columnDefinition = "TEXT")
    private String content_story;

    @Builder
    public MyBookContent(String image){
        this.image = image;
    }
}
