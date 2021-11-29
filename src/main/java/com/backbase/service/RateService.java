package com.backbase.service;

import com.backbase.data.entity.MovieEntity;
import com.backbase.data.entity.RateEntity;
import com.backbase.data.entity.UserEntity;
import com.backbase.data.repository.RateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RateService {

    private final RateRepository rateRepository;

    public RateEntity saveAndGetUserRate(UserEntity user, MovieEntity movie, BigDecimal rate) {
        Optional<RateEntity> optionalUserRate = rateRepository.findByUserAndMovie(user, movie);
        RateEntity userRate;
        userRate = optionalUserRate.orElseGet(RateEntity::new);
        userRate.setRate(rate);
        userRate.setMovie(movie);
        userRate.setUser(user);
        rateRepository.save(userRate);
        return userRate;
    }
}
