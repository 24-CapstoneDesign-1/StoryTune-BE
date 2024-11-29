package com.capstone.storytune.domain.mybook.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class MyBookCharacter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "myBookId")
    private MyBook myBook;

    private String image;

    @Builder
    public MyBookCharacter(MyBook myBook, String image) {
        this.myBook = myBook;
        this.image = image;
    }

    public void updateName(String newName) {
        this.name = newName;
    }
}
