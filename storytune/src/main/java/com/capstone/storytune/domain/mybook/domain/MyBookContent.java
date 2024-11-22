package com.capstone.storytune.domain.mybook.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    private MyBookCharacter character;

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

    public void updateGuide(String guide){
        this.guide = guide;
    }

    public void updateStory(String content, String scenario, String story, boolean isLine, MyBookCharacter character){
        this.content = content;
        this.content_scenario = scenario;
        this.content_story = story;
        this.isLine = isLine;
        this.character = character;
    }
}
