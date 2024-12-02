package com.capstone.storytune.domain.user.service;

import com.capstone.storytune.domain.user.domain.Friend;
import com.capstone.storytune.domain.user.domain.FriendStatus;
import com.capstone.storytune.domain.user.domain.User;
import com.capstone.storytune.domain.user.dto.request.FriendUpdateRequest;
import com.capstone.storytune.domain.user.dto.request.LoginRequest;
import com.capstone.storytune.domain.user.dto.request.SignupRequest;
import com.capstone.storytune.domain.user.dto.response.*;
import com.capstone.storytune.domain.user.exception.*;
import com.capstone.storytune.domain.user.repository.FriendRepository;
import com.capstone.storytune.domain.user.repository.UserRepository;
import com.capstone.storytune.domain.user.jwt.TokenGenerator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.capstone.storytune.global.dto.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenGenerator tokenGenerator;
    private final FriendRepository friendRepository;

    @Transactional
    public void signUp(SignupRequest request){
        User user = User.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .name(request.name())
                .age(request.age())
                .gender(request.gender())
                .build();

        userRepository.save(user);
    }

    @Transactional
    public LoginResponse login(LoginRequest request){
        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new NotFoundUserNameException(NOT_FOUND_USER_NAME_EXCEPTION));

        if(!passwordEncoder.matches(request.password(), user.getPassword())){
            throw new WrongPasswordException(WRONG_PASSWORD_EXCEPTION);
        }

        String accessToken = tokenGenerator.generateAccessToken(user.getId());
        String refreshToken = tokenGenerator.generateRefreshToken(user.getId());
        return LoginResponse.of(accessToken, refreshToken);
    }


    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundUserIdException(NOT_FOUND_USER_ID_EXCEPTION));
    }

    public UserSearchResponse getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundUserNameException(NOT_FOUND_USER_NAME_EXCEPTION));
        return UserSearchResponse.of(user);
    }

    public void createFriend(Long userId, User user){
        //  친구 찾기
        User friend = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundUserIdException(NOT_FOUND_USER_ID_EXCEPTION));

        // 이미 친구 관계인지 확인
        if(friendRepository.existsByUserAndFriend(user, friend) || friendRepository.existsByUserAndFriend(friend, user)){
            throw new DuplicatedFriendException(DUPLICATED_FRIEND_EXCEPTION);
        }

        // Friend 객체 생성 (양방향)
        Friend friendRequest1 = Friend.builder()
                .user(user)
                .friend(friend)
                .build();

        Friend friendRequest2 = Friend.builder()
                .user(friend)
                .friend(user)
                .build();

        friendRepository.save(friendRequest1);
        friendRepository.save(friendRequest2);
    }

    public List<FriendRequestResponse> getFriendRequest(User user) {
        // 나한테 연결된 friend 중, status가 pending인거 찾기
        List<Friend> receiveFriendRequests = friendRepository.findAllByFriendAndStatus(user, FriendStatus.PENDING);

        return receiveFriendRequests.stream()
                .map(FriendRequestResponse::of)
                .toList();
    }

    public void updateFriendStatus(Long friendId, FriendUpdateRequest request){
        // Friend 객체 찾기
        Friend friend = friendRepository.findById(friendId)
                .orElseThrow(() -> new NotFoundFriendIdException(NOT_FOUND_FRIEND_EXCEPTION));

        // 수락/거절 여부 체크
        friend.updateStatus(request.status());
        friendRepository.save(friend);

        // 양방향 처리
        Friend reverseFriend = friendRepository.findByUserAndFriend(friend.getFriend(), friend.getUser())
                .orElseThrow(() -> new NotFoundReverseFriendException(NOT_FOUND_REVERSE_FRIEND_EXCEPTION));
        reverseFriend.updateStatus(request.status());
        friendRepository.save(reverseFriend);
    }

    public List<FriendResponse> getFriends(User user){
        // 친구 찾기 (user -> 나로 설정되어 있고, status -> accepted)
        List<Friend> friends = friendRepository.findAllByUserAndStatus(user, FriendStatus.ACCEPTED);
        return friends.stream()
                .map(FriendResponse::of)
                .toList();
    }

    public CurrentUserResponse getCurrentUser(User user){
        return CurrentUserResponse.of(user);
    }
}
