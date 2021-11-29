package com.backbase.service;

import com.backbase.data.entity.MovieEntity;
import com.backbase.data.entity.RateEntity;
import com.backbase.data.entity.UserEntity;
import com.backbase.data.repository.MovieRepository;
import com.backbase.exception.CustomServiceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.backbase.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class MovieServiceTest {

    @InjectMocks
    MovieService movieService;

    @Mock
    MovieRepository movieRepository;
    @Mock
    UserService userService;
    @Mock
    RateService rateService;

    @Test
    @DisplayName("rate should be work correctly")
    void rate_shouldBeWorkCorrectly() {
        UserEntity mockedUser = mockedUserEntity();
        MovieEntity mockedMovie = mockedMovieEntity();
        RateEntity mockedRate = mockedRateEntity();
        when(movieRepository.findByTitle(mockedMovie.getTitle())).thenReturn(Optional.of(mockedMovie));
        when(userService.findUser("username")).thenReturn(mockedUser);
        when(rateService.saveAndGetUserRate(mockedUser, mockedMovie, BigDecimal.valueOf(8.5))).thenReturn(mockedRate);
        RateEntity actualRate = movieService.rate(BigDecimal.valueOf(8.5), mockedMovie.getTitle(), String.valueOf(mockedMovie.getBoxOffice()), mockedUser.getUsername());
        assertNotNull(actualRate);
    }

    @Test
    @DisplayName("getTopTenBasedOnBoxOffice_shouldReturnsListOfTopTenMoviesBasedOnBoxOffice_whenExisting")
    void getTopTenBasedOnBoxOffice_shouldReturnsListOfTopTenMoviesBasedOnBoxOffice_whenExisting() {
        List<MovieEntity> mockedListMovieEntity = mockedListMovieEntity();
        when(movieRepository.findTop10BasedOnBoxOffice()).thenReturn(mockedListMovieEntity);
        List<MovieEntity> topTenBasedOnBoxOffice = movieService.getTopTenBasedOnBoxOffice();
        assertNotNull(topTenBasedOnBoxOffice);
    }

    @Test
    @DisplayName("getTopTenBasedOnBoxOffice should throws new CustomException, when NothingFound")
    void getTopTenBasedOnBoxOffice_shouldThrowsNewCustomServiceException_whenNotExisting() {
        when(movieRepository.findTop10BasedOnBoxOffice()).thenReturn(new ArrayList<>());
        CustomServiceException thrown = assertThrows(
                CustomServiceException.class,
                () -> movieService.getTopTenBasedOnBoxOffice(),
                "The top ten is not available"
        );
        assertTrue(thrown.getMessage().contains("The top ten is not available"));
    }
}