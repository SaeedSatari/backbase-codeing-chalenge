package com.backbase.service;

import com.backbase.data.entity.UserEntity;
import com.backbase.data.repository.UserRepository;
import com.backbase.exception.CustomServiceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static com.backbase.TestUtils.mockedUserEntity;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @Test
    @DisplayName("findUser should returns UserEntity, when find the user")
    void findUser_shouldReturnsUserEntity_whenFindTheUser() {
        UserEntity mockedUserEntity = mockedUserEntity();
        when(userRepository.findByUsername(mockedUserEntity.getUsername())).thenReturn(Optional.of(mockedUserEntity));
        UserEntity actualUser = userService.findUser(mockedUserEntity.getUsername());
        assertNotNull(actualUser);
        assertEquals(mockedUserEntity.getUsername(), actualUser.getUsername());
        assertEquals(mockedUserEntity.getPassword(), actualUser.getPassword());
    }

    @Test
    @DisplayName("findUser should throws CustomServiceException, when can not find the user")
    void findUser_shouldThrowsCustomServiceException_whenCanNotFindTheUser() {
        UserEntity mockedUserEntity = mockedUserEntity();
        when(userRepository.findByUsername(mockedUserEntity.getUsername())).thenReturn(Optional.empty());
        CustomServiceException thrown = assertThrows(
                CustomServiceException.class,
                () -> userService.findUser(mockedUserEntity.getUsername()),
                "User not found: "
        );
        assertTrue(thrown.getMessage().contains("User not found: "));
    }
}