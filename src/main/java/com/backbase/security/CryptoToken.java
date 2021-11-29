package com.backbase.security;


import com.backbase.data.entity.UserEntity;
import com.backbase.exception.CustomServiceException;
import com.backbase.exception.ErrorCode;
import com.backbase.security.model.Header;
import com.backbase.security.model.Payload;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.util.Base64;

@Slf4j
public class CryptoToken {

    public static String generateToken(UserEntity user) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        Header header = new Header("HS256", "JWT");

        Payload payload = new Payload(user.getId(), user.getUsername(), user.getTokenExpireDate());
        String headerString;
        String payLoadString;
        try {
            headerString = Base64.getEncoder().encodeToString(mapper.writeValueAsString(header).getBytes());
            payLoadString = Base64.getEncoder().encodeToString(mapper.writeValueAsString(payload).getBytes());

        } catch (JsonProcessingException exception) {
            log.error(exception.getMessage() + ",username: %s", user.getUsername());
            throw new CustomServiceException("Internal server error", ErrorCode.INTERNAL_ERROR, HttpStatus.INTERNAL_SERVER_ERROR, user.getUsername());
        }
        return headerString + "." + payLoadString;
    }

    public static String getUsername(String token) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        String[] tokenParts = token.split("\\.");
        String payloadString = new String(Base64.getDecoder().decode(tokenParts[1].getBytes()));

        String username;
        try {
            username = mapper.readValue(payloadString, Payload.class).getUsername();
        } catch (JsonProcessingException exception) {
            log.error(exception.getMessage() + ",token: %s", token);
            throw new CustomServiceException("Internal server error", ErrorCode.INTERNAL_ERROR, HttpStatus.INTERNAL_SERVER_ERROR, token);
        }
        return username;
    }

    public static String getTokenUsername(String authorization) {
        String token = authorization.substring(7);
        return getUsername(token);
    }

}

