package com.capstone.storytune.domain.roleplaying.service;

import com.capstone.storytune.domain.mybook.domain.MyBook;
import com.capstone.storytune.domain.mybook.domain.MyBookCharacter;
import com.capstone.storytune.domain.mybook.domain.MyBookContent;
import com.capstone.storytune.domain.mybook.exception.NotFoundMyBookContentException;
import com.capstone.storytune.domain.mybook.exception.NotFoundMyBookIdException;
import com.capstone.storytune.domain.mybook.repository.MyBookCharacterRepository;
import com.capstone.storytune.domain.mybook.repository.MyBookContentRepository;
import com.capstone.storytune.domain.mybook.repository.MyBookRepository;
import com.capstone.storytune.domain.roleplaying.domain.InviteStatus;
import com.capstone.storytune.domain.roleplaying.domain.Participant;
import com.capstone.storytune.domain.roleplaying.domain.RolePlayingRoom;
import com.capstone.storytune.domain.roleplaying.dto.request.ParticipantRequest;
import com.capstone.storytune.domain.roleplaying.dto.request.ParticipantUpdateRequest;
import com.capstone.storytune.domain.roleplaying.dto.response.*;
import com.capstone.storytune.domain.roleplaying.exception.NotFoundParticipantIdException;
import com.capstone.storytune.domain.roleplaying.exception.NotFoundRolePlayingRoomIdException;
import com.capstone.storytune.domain.roleplaying.repository.ParticipantRepository;
import com.capstone.storytune.domain.roleplaying.repository.RolePlayingRoomRepository;
import com.capstone.storytune.domain.user.domain.User;
import com.capstone.storytune.domain.user.exception.NotFoundUserIdException;
import com.capstone.storytune.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.capstone.storytune.global.dto.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Builder
@Transactional
public class RolePlayingService {
    private final RolePlayingRoomRepository rolePlayingRoomRepository;
    private final UserRepository userRepository;
    private final ParticipantRepository participantRepository;
    private final MyBookRepository myBookRepository;
    private final MyBookCharacterRepository myBookCharacterRepository;
    private final MyBookContentRepository myBookContentRepository;

    public RolePlayingRoomCreateResponse createRolePlayingRoom(User user){
        RolePlayingRoom rolePlayingRoom = RolePlayingRoom.builder()
                .owner(user)
                .build();
        rolePlayingRoomRepository.save(rolePlayingRoom);

        // 나는 참가자로 바로 넣기
        Participant participant = Participant.builder()
                .rolePlayingRoom(rolePlayingRoom)
                .user(user)
                .status(InviteStatus.ACCEPTED)
                .build();
        participantRepository.save(participant);

        return RolePlayingRoomCreateResponse.of(rolePlayingRoom);
    }

    public void createParticipant(ParticipantRequest request){
        // user 찾기
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new NotFoundUserIdException(NOT_FOUND_USER_ID_EXCEPTION));

        // rolePlayingroom 찾기
        RolePlayingRoom rolePlayingRoom = rolePlayingRoomRepository.findById(request.rolePlayingRoomId())
                .orElseThrow(() -> new NotFoundRolePlayingRoomIdException(NOT_FOUND_ROLE_PLAYING_ROOM_ID_EXCEPTION));

        // participant 생성
        Participant participant = Participant.builder()
                .user(user)
                .rolePlayingRoom(rolePlayingRoom)
                .status(InviteStatus.PENDING)
                .build();
        participantRepository.save(participant);
    }

    public List<InviteRequestResponse> getInviteRequest(User user){
        List<Participant> participants = participantRepository.findByUserAndStatus(user, InviteStatus.PENDING);

        return participants.stream()
                .map(InviteRequestResponse::of)
                .toList();
    }

    public void updateInviteRequest(Long participantId, ParticipantUpdateRequest request){
        // participant 객체 찾기
        Participant participant = participantRepository.findById(participantId)
                .orElseThrow(() -> new NotFoundParticipantIdException(NOT_FOUND_PARTICIPANT_ID_EXCEPTION));

        // 상태 업데이트
        participant.updateStatus(request.status());
        participantRepository.save(participant);
    }

    public List<ParticipantResponse> getParticipant(Long rolePlayingRoomId){
        // roleplaying 객체 찾기
        RolePlayingRoom rolePlayingRoom = rolePlayingRoomRepository.findById(rolePlayingRoomId)
                .orElseThrow(() -> new NotFoundRolePlayingRoomIdException(NOT_FOUND_ROLE_PLAYING_ROOM_ID_EXCEPTION));

        // accepted 인 participants만 찾기
        List<Participant> participants = participantRepository.findByRolePlayingRoomAndStatus(rolePlayingRoom, InviteStatus.ACCEPTED);

        return participants.stream()
                .map(ParticipantResponse::of)
                .toList();
    }

    public List<RoleResponse> getRole(Long rolePlayingRoomId, Long myBookId){
        RolePlayingRoom rolePlayingRoom = rolePlayingRoomRepository.findById(rolePlayingRoomId)
                .orElseThrow(() -> new NotFoundRolePlayingRoomIdException(NOT_FOUND_ROLE_PLAYING_ROOM_ID_EXCEPTION));

        MyBook myBook = myBookRepository.findById(myBookId)
                .orElseThrow(() -> new NotFoundMyBookIdException(NOT_FOUND_MY_BOOK_ID_EXCEPTION));

        List<Participant> participants = participantRepository.findByRolePlayingRoomAndStatus(rolePlayingRoom, InviteStatus.ACCEPTED);

        List<MyBookCharacter> characters = myBookCharacterRepository.findByMyBook(myBook);

        if(participants.isEmpty() || characters.isEmpty()){
            throw new IllegalArgumentException("참가자 또는 등장인물이 없습니다.");
        }

        // 역할 배정
        List<RoleResponse> roleAssignments = new ArrayList<>();
        int participantIndex = 0;

        for(MyBookCharacter character: characters){
            Participant participant = participants.get(participantIndex);

            participant.updateCharacter(character);
            participantRepository.save(participant);

            roleAssignments.add(RoleResponse.of(participant));

            participantIndex = (participantIndex + 1) % participants.size();
        }

        return roleAssignments;
    }

    public RolePlayingResponse getRolePlayingDetail(Long rolePlayingRoomId, Long myBookId, int pageNum){
        RolePlayingRoom rolePlayingRoom = rolePlayingRoomRepository.findById(rolePlayingRoomId)
                .orElseThrow(() -> new NotFoundRolePlayingRoomIdException(NOT_FOUND_ROLE_PLAYING_ROOM_ID_EXCEPTION));

        MyBook myBook = myBookRepository.findById(myBookId)
                .orElseThrow(() -> new NotFoundMyBookIdException(NOT_FOUND_MY_BOOK_ID_EXCEPTION));

        MyBookContent myBookContent = myBookContentRepository.findByMyBookAndPage(myBook, pageNum)
                .orElseThrow(() -> new NotFoundMyBookContentException(NOT_FOUND_MY_BOOK_CONTENT_EXCEPTION));


        List<Participant> participants = participantRepository.findByRolePlayingRoomAndStatus(rolePlayingRoom, InviteStatus.ACCEPTED);

        Participant matchingParticipant = participants.stream()
                .filter(participant -> participant.getCharacter() != null)
                .filter(participant -> participant.getCharacter().equals(myBookContent.getMyBookCharacter()))
                .findFirst()
                .orElse(null);

        return RolePlayingResponse.of(matchingParticipant, myBookContent);
    }

}
