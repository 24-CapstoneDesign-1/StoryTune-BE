package com.capstone.storytune.domain.roleplaying.repository;

import com.capstone.storytune.domain.roleplaying.domain.InviteStatus;
import com.capstone.storytune.domain.roleplaying.domain.Participant;
import com.capstone.storytune.domain.roleplaying.domain.RolePlayingRoom;
import com.capstone.storytune.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    List<Participant> findByUserAndStatus(User user, InviteStatus status);
    List<Participant> findByRolePlayingRoomAndStatus(RolePlayingRoom rolePlayingRoom, InviteStatus status);
}
