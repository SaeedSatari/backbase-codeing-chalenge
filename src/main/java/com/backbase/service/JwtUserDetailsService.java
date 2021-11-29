package com.backbase.service;


import com.backbase.data.entity.UserEntity;
import com.backbase.data.repository.UserRepository;
import com.backbase.exception.CustomServiceException;
import com.backbase.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Optional<UserEntity> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            return new User(optionalUser.get().getUsername(), optionalUser.get().getPassword(), new ArrayList<>());
        }
        throw new UsernameNotFoundException("User not found with username: " + username);
    }

    public UserEntity loadUserEntityByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        throw new UsernameNotFoundException("User not found with username: " + username);
    }

    public UserEntity save(UserEntity newUser) {
        newUser.setPassword(bcryptEncoder.encode(newUser.getPassword()));
        newUser.setTokenExpireDate(LocalDateTime.now().plusDays(1));
        try {
            newUser = userRepository.save(newUser);
        } catch (DataIntegrityViolationException exception) {
            throw new CustomServiceException("User already exist", ErrorCode.USER_EXIST, HttpStatus.BAD_REQUEST, newUser.getUsername());
        }
        return newUser;
    }
}