package com.backbase.service;

import com.backbase.data.entity.MovieEntity;
import com.backbase.data.entity.RateEntity;
import com.backbase.data.entity.UserEntity;
import com.backbase.data.repository.RateRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static com.backbase.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class RateServiceTest {

    @InjectMocks
    RateService rateService;
    @Mock
    RateRepository rateRepository;

    @Test
    void saveAndGetUserRate() {
        RateEntity rateEntity = mockedRateEntity();
        UserEntity userEntity = mockedUserEntity();
        MovieEntity movieEntity = mockedMovieEntity();
        when(rateRepository.findByUserAndMovie(userEntity, movieEntity)).thenReturn(Optional.of(rateEntity));
        when(rateRepository.save(rateEntity)).thenReturn(rateEntity);
        RateEntity actualRate = rateService.saveAndGetUserRate(userEntity, movieEntity, BigDecimal.valueOf(8.5));
        assertNotNull(actualRate);
    }
}