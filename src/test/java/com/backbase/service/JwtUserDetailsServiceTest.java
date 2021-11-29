package com.backbase.service;

import com.backbase.data.entity.UserEntity;
import com.backbase.data.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static com.backbase.TestUtils.mockedUserEntity;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class JwtUserDetailsServiceTest {

    private final static String username = "mocked-username";

    @InjectMocks
    JwtUserDetailsService jwtUserDetailsService;

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder bcryptEncoder;

    @Test
    @DisplayName("loadUserByUsername should returns UserEntity, when username is existing")
    void loadUserByUsername_shouldReturnsUser_whenUsernameIsExisting() {
        UserEntity mockedUser = mockedUserEntity();
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(mockedUser));
        UserDetails actualUser = jwtUserDetailsService.loadUserByUsername(username);
        assertNotNull(actualUser);
    }

    @Test
    @DisplayName("loadUserByUsername should throws new UsernameNotFoundException, when username Not Found")
    void loadUserByUsername_shouldThrowsNewUsernameNotFoundException_whenUsernameNotFound() {
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        UsernameNotFoundException thrown = assertThrows(
                UsernameNotFoundException.class,
                () -> jwtUserDetailsService.loadUserByUsername(username),
                "User not found with username: "
        );
        assertTrue(thrown.getMessage().contains("User not found with username: "));
    }

    @Test
    @DisplayName("loadUserEntityByUsername should returns UserEntity, when username is existing")
    void loadUserEntityByUsername_shouldReturnsUserEntity_whenUsernameIsExisting() {
        UserEntity mockedUser = mockedUserEntity();
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(mockedUser));
        UserEntity actualUserEntity = jwtUserDetailsService.loadUserEntityByUsername(username);
        assertNotNull(actualUserEntity);
    }

    @Test
    @DisplayName("loadUserEntityByUsername should returns UserEntity, when username is existing")
    void loadUserEntityByUsername_shouldThrowsNewUsernameNotFoundException_whenUsernameNotFound() {
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        UsernameNotFoundException thrown = assertThrows(
                UsernameNotFoundException.class,
                () -> jwtUserDetailsService.loadUserEntityByUsername(username),
                "User not found with username: "
        );
        assertTrue(thrown.getMessage().contains("User not found with username: "));
    }

    @Test
    @DisplayName("save should save UserEntity with encoded password")
    void save_shouldSaveUserEntityWithEncodedPassword() {
        UserEntity mockedUserEntity = mockedUserEntity();
        when(bcryptEncoder.encode(mockedUserEntity.getPassword())).thenReturn("encoded-string");
        when(userRepository.save(mockedUserEntity)).thenReturn(mockedUserEntity);
        UserEntity savedUserEntity = jwtUserDetailsService.save(mockedUserEntity);
        assertNotNull(savedUserEntity);
    }
}