package com.capstone.storytune.domain.user.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String username; // 아이디
    private String password;
    private String name;
    private int age;

    @Enumerated(EnumType.STRING)
    private Gender gender;
}
