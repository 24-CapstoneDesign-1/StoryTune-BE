package com.capstone.storytune.domain.user.service;

import com.capstone.storytune.domain.user.domain.User;
import com.capstone.storytune.domain.user.dto.request.LoginRequest;
import com.capstone.storytune.domain.user.dto.request.SignupRequest;
import com.capstone.storytune.domain.user.dto.response.LoginResponse;
import com.capstone.storytune.domain.user.exception.NotFoundUserIdException;
import com.capstone.storytune.domain.user.exception.NotFoundUserNameException;
import com.capstone.storytune.domain.user.exception.WrongPasswordException;
import com.capstone.storytune.domain.user.repository.UserRepository;
import com.capstone.storytune.domain.user.jwt.TokenGenerator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.capstone.storytune.global.dto.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenGenerator tokenGenerator;

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
}
