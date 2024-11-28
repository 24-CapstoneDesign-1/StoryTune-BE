package com.capstone.storytune.domain.roleplaying.domain;

import com.capstone.storytune.domain.mybook.domain.MyBookCharacter;
import com.capstone.storytune.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rolePlayingRoomId")
    private RolePlayingRoom rolePlayingRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @Enumerated(EnumType.STRING)
    private InviteStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "myBookCharacterId")
    private MyBookCharacter character;

    @Builder
    public Participant(User user, RolePlayingRoom rolePlayingRoom, InviteStatus status, MyBookCharacter character) {
        this.user = user;
        this.rolePlayingRoom = rolePlayingRoom;
        this.status = status;
    }

    public void updateStatus(InviteStatus status){
        this.status = status;
    }

    public void updateCharacter(MyBookCharacter character){
        this.character = character;
    }
}
