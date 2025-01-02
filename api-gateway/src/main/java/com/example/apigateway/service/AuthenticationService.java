package com.example.apigateway.service;
//

import com.example.apigateway.dto.request.AuthenticationRequest;
import com.example.apigateway.dto.request.LogoutRequest;
import com.example.apigateway.dto.response.AuthenticationResponse;
import com.example.apigateway.dto.response.UsersDTO;
import com.example.apigateway.entity.InvalidatedToken;
import com.example.apigateway.repository.InvalidatedTokenRepository;
import com.netflix.discovery.converters.Auto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.security.Key;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class AuthenticationService {

    private static final long tokenExpTime = 60 * 60 * 1000; // 1 hour

    @Autowired
    private UserInfoUserDetailsService userDetailsService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private InvalidatedTokenRepository invalidatedTokenRepository;

    public AuthenticationResponse authenticate(AuthenticationRequest request){
        var user = userDetailsService.callApiGetUserByUserName(request.getUsername());

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!authenticated)
            throw new RuntimeException("Invalid password");

        var token = jwtService.generateToken(request.getUsername(), tokenExpTime);
        return new AuthenticationResponse(token, true);
    }

    public void logout(LogoutRequest request) {
        String jit = jwtService.extractJwtId(request.getToken());
        Date expiryTime = jwtService.extractExpiration(request.getToken());

        InvalidatedToken invalidatedToken = InvalidatedToken
                .builder()
                .id(jit)
                .expiryTime(expiryTime)
                .build();
        invalidatedTokenRepository.save(invalidatedToken);
    }
}
