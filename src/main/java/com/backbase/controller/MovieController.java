package com.backbase.controller;

import com.backbase.client.OMDBClient;
import com.backbase.controller.request.RateRequest;
import com.backbase.controller.response.BoxOfficeResponse;
import com.backbase.controller.response.RateResponse;
import com.backbase.data.entity.MovieEntity;
import com.backbase.data.entity.RateEntity;
import com.backbase.security.CryptoToken;
import com.backbase.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;
    private final OMDBClient omdbClient;

    @PostMapping("/rates")
    @ResponseStatus(HttpStatus.CREATED)
    public RateResponse rateMovie(@RequestBody RateRequest request, @RequestHeader("Authorization") String authorization) {
        JSONObject movieDetails = omdbClient.getMovieDetails(request.getMovieTitle());
        String boxOffice = movieDetails.get("BoxOffice").toString();
        RateEntity rate = movieService.rate(request.getRate(), request.getMovieTitle(), boxOffice, CryptoToken.getTokenUsername(authorization));
        if (rate != null) {
            return RateResponse.builder().movieTitle(rate.getMovie().getTitle()).rate(rate.getRate()).rateStatus("Rated Successfully!").build();
        } else {
            return RateResponse.builder().movieTitle(request.getMovieTitle()).rate(request.getRate()).rateStatus("Rated Rejected!").build();
        }
    }

    @GetMapping("/top-ten")
    @ResponseStatus(HttpStatus.OK)
    public List<BoxOfficeResponse> getTopTenMoviesBasedOnBoxOffice() {
        List<MovieEntity> topTenMovies = movieService.getTopTenBasedOnBoxOffice();
        List<BoxOfficeResponse> responses = new ArrayList<>();
        for (MovieEntity movieEntity : topTenMovies) {
            NumberFormat n = NumberFormat.getCurrencyInstance(Locale.US);
            String s = n.format(movieEntity.getBoxOffice() / 100.0);
            BoxOfficeResponse boxOfficeResponse = BoxOfficeResponse.builder()
                    .title(movieEntity.getTitle())
                    .boxOffice(s)
                    .build();
            responses.add(boxOfficeResponse);
        }
        return responses;
    }
}
