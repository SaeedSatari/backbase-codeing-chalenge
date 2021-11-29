package com.backbase.service;

import com.backbase.data.entity.UserEntity;
import com.backbase.data.repository.UserRepository;
import com.backbase.exception.CustomServiceException;
import com.backbase.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserEntity findUser(String username) {
        Optional<UserEntity> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new CustomServiceException("User not found: ", ErrorCode.USER_NOT_FOUND, HttpStatus.BAD_REQUEST, username);
        }
    }
}
