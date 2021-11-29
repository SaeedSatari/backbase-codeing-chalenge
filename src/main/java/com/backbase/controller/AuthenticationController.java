package com.backbase.controller;


import com.backbase.controller.request.JwtRequest;
import com.backbase.controller.request.UserRequest;
import com.backbase.controller.response.JwtResponse;
import com.backbase.data.entity.UserEntity;
import com.backbase.security.CryptoToken;
import com.backbase.service.JwtUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/authenticate")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtUserDetailsService userDetailsService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public JwtResponse login(@RequestBody JwtRequest authenticationRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                authenticationRequest.getPassword()));
        UserEntity userEntity = userDetailsService.loadUserEntityByUsername(authenticationRequest.getUsername());
        String token = CryptoToken.generateToken(userEntity);
        return JwtResponse.builder().jwtToken(token).build();
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public JwtResponse register(@RequestBody UserRequest user) {
        UserEntity userEntity = userDetailsService.save(UserEntity.builder().username(user.getUsername()).password(user.getPassword()).build());
        String token = CryptoToken.generateToken(userEntity);
        return JwtResponse.builder().jwtToken(token).build();
    }
}