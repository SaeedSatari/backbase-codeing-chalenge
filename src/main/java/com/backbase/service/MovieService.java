package com.backbase.service;

import com.backbase.data.entity.MovieEntity;
import com.backbase.data.entity.RateEntity;
import com.backbase.data.entity.UserEntity;
import com.backbase.data.repository.MovieRepository;
import com.backbase.exception.CustomServiceException;
import com.backbase.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final UserService userService;
    private final RateService rateService;

    private MovieEntity findMovie(String title, String username) {
        if (title == null) {
            throw new CustomServiceException("The movie not found", ErrorCode.MOVIE_NOT_FOUND, HttpStatus.BAD_REQUEST, username);
        }
        Optional<MovieEntity> optionalMovie = movieRepository.findByTitle(title);
        return optionalMovie.orElse(null);
    }

    public RateEntity rate(BigDecimal rate, String movieTitle, String boxOffice, String username) {
        String convertedBoxOffice = boxOfficeValidation(boxOffice, username);

        MovieEntity movie = findMovie(movieTitle, username);
        if (movie == null) {
            movie = buildAndSaveMovie(movieTitle, convertedBoxOffice);
        }
        rateValidation(rate, username);
        UserEntity user = userService.findUser(username);
        return rateService.saveAndGetUserRate(user, movie, rate);
    }

    private MovieEntity buildAndSaveMovie(String movieTitle, String convertedBoxOffice) {
        MovieEntity movie = MovieEntity.builder().boxOffice(Long.valueOf(convertedBoxOffice)).title(movieTitle).build();
        return movieRepository.save(movie);
    }

    private void rateValidation(BigDecimal rate, String username) {
        if (rate == null || rate.compareTo(BigDecimal.ZERO) < 0 || rate.compareTo(BigDecimal.TEN) > 10) {
            throw new CustomServiceException("The rate should between 0 and 10", ErrorCode.RATE_INVALID_RANGE, HttpStatus.BAD_REQUEST, username);
        }
    }

    private String boxOfficeValidation(String boxOffice, String username) {
        String convertedBoxOffice = boxOffice.replace(",", "").replace("$", "");
        if (!convertedBoxOffice.matches("\\d*")) {
            throw new CustomServiceException("The Box office not sent", ErrorCode.BOX_OFFICE_NOT_FOUND, HttpStatus.BAD_REQUEST, username);
        }
        return convertedBoxOffice;
    }

    public List<MovieEntity> getTopTenBasedOnBoxOffice() {
        List<MovieEntity> topTenMovies = movieRepository.findTop10BasedOnBoxOffice();
        if (!topTenMovies.isEmpty()) {
            return topTenMovies;
        }
        throw new CustomServiceException("The top ten is not available", ErrorCode.TOP_TEN_NOT_FOUND, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
