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

    private boolean isLine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "myBookCharacterId", nullable = true) // 등장인물이 없을 수 있음
    private MyBookCharacter myBookCharacter;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(columnDefinition = "TEXT")
    private String content_scenario;

    @Column(columnDefinition = "TEXT")
    private String content_story;

    @Builder
    public MyBookContent(String image, int page, MyBook myBook){
        this.image = image;
        this.page = page;
        this.myBook = myBook;
    }

    public void updateStory(String content, String scenario, String story, boolean isLine, MyBookCharacter character){
        this.content = content;
        this.content_scenario = scenario;
        this.content_story = story;
        this.isLine = isLine;

        // 대사일 경우 등장인물 설정, 해설일 경우 null 처리
        this.myBookCharacter = isLine ? character : null;
    }

    public void updateInEnglish(String translatedScenario, String translatedStory){
        this.content_story = translatedStory;
        this.content_scenario = translatedScenario;
    }
}
