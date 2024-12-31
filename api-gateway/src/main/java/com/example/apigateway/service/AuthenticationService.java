package com.example.apigateway.service;
//

import com.example.apigateway.dto.request.AuthenticationRequest;
import com.example.apigateway.dto.response.AuthenticationResponse;
import com.example.apigateway.dto.response.UsersDTO;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

//
//;
//
@Service
public class AuthenticationService {
//
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    private static final long tokenExpTime = 60 * 60 * 1000; // 1 hour

    @Autowired
    private UserInfoUserDetailsService userDetailsService;

    public AuthenticationResponse authenticate(AuthenticationRequest request){
        var user = userDetailsService.callApiGetUserByUserName(request.getUsername());

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!authenticated)
            throw new RuntimeException("Invalid password");

        var token = generateToken(request.getUsername(), tokenExpTime);
        return new AuthenticationResponse(token, true);
    }

    public String generateToken(String userName, Long expTime) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userName, expTime);
    }

    private String createToken(Map<String, Object> claims, String username, Long expTime) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuer("api-gateway")
                .setIssuedAt(new Date())
                .setId(UUID.randomUUID().toString())
                .setExpiration(new Date(System.currentTimeMillis() + expTime))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SIGNER_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

////    private String buildScope(UsersDTO user){
////        StringJoiner stringJoiner = new StringJoiner(" ");
////        if (!CollectionUtils.isEmpty(user.getRoles()))
////            user.getRoles().forEach(stringJoiner::add);
////        return stringJoiner.toString();
////    }
}
