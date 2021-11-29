package com.backbase.client;

import com.backbase.exception.CustomServiceException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class OMDBClient {

    @Value("${omdb.url}")
    protected String HOST_NAME;

    @Value("${omdb.api.key}")
    private String API_KEY;

    public JSONObject getMovieDetails(String name) {
        HttpResponse<JsonNode> movieDetailsResponse;
        try {
            movieDetailsResponse = getDetails(name);
            if (movieDetailsResponse.getStatus() != 200) {
                log.error("Error in getting movies response, reason = {}", (movieDetailsResponse.getBody().toString()));
                throw new CustomServiceException("Error in getting movies response");
            }
        } catch (UnirestException e) {
            log.error("Error in OMDB client");
            throw new CustomServiceException(e.getMessage());
        }
        return movieDetailsResponse.getBody().getObject();
    }

    private HttpResponse<JsonNode> getDetails(String name) throws UnirestException {
        HttpResponse<JsonNode> movieDetailsResponse;
        movieDetailsResponse = Unirest.get(HOST_NAME + API_KEY + "&t=" + encode(name))
                .header("Accept", "*/*")
                .header("Content-Type", "application/json")
                .asJson();
        return movieDetailsResponse;
    }

    public static String encode(String queryParameter) {
        return URLEncoder.encode(queryParameter, StandardCharsets.UTF_8);
    }
}