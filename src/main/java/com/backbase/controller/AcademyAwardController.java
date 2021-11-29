package com.backbase.controller;

import com.backbase.client.OMDBClient;
import com.backbase.controller.request.AcademyAwardRequest;
import com.backbase.controller.response.AcademyAwardResponse;
import com.backbase.data.entity.AcademyAwardEntity;
import com.backbase.service.AcademyAwardService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/academy-awards")
@RequiredArgsConstructor
public class AcademyAwardController {

    private final AcademyAwardService academyAwardService;
    private final OMDBClient omdbClient;

    @GetMapping("/best-picture-winner")
    @ResponseStatus(HttpStatus.OK)
    public AcademyAwardResponse isBestPictureWinner(@RequestBody AcademyAwardRequest academyAwardRequest) {
        AcademyAwardEntity bestPictureAcademyAward = academyAwardService.findBestPictureAcademyAward(academyAwardRequest.getMovieTitle());
        JSONObject movieDetails = omdbClient.getMovieDetails(academyAwardRequest.getMovieTitle());
        if (movieDetails.get("Response").equals("True") && movieDetails.get("Awards") != "N/A") {
            if (bestPictureAcademyAward != null) {
                return AcademyAwardResponse.builder().won(bestPictureAcademyAward.isWon()).nominee(bestPictureAcademyAward.getNominee()).category(bestPictureAcademyAward.getCategory()).build();
            } else {
                return AcademyAwardResponse.builder().won(false).nominee(academyAwardRequest.getMovieTitle()).category("Best Picture").build();
            }
        } else {
            return AcademyAwardResponse.builder().won(false).nominee(academyAwardRequest.getMovieTitle()).category("Best Picture").build();
        }
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AcademyAwardResponse> listAcademyAwards(@RequestParam(defaultValue = "0") Integer pageNo,
                                                        @RequestParam(defaultValue = "10") Integer pageSize,
                                                        @RequestParam(defaultValue = "nominee") String sortBy) {
        Page<AcademyAwardEntity> academyAwards = academyAwardService.getAcademyAwards(pageNo, pageSize, sortBy);
        return academyAwards
                .stream()
                .map(this::buildAcademyAwardResponse)
                .collect(Collectors.toList());
    }

    private AcademyAwardResponse buildAcademyAwardResponse(AcademyAwardEntity academyAwardEntity) {
        return AcademyAwardResponse
                .builder()
                .won(academyAwardEntity.isWon())
                .category(academyAwardEntity.getCategory())
                .nominee(academyAwardEntity.getNominee())
                .build();
    }
}
