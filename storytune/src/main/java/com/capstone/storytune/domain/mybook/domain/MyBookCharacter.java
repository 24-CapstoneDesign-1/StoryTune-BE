package com.capstone.storytune.domain.mybook.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class MyBookCharacter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "myBookCharacter_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "myBookId")
    private MyBook myBook;

    private String name;
}
