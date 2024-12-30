package com.example.apigateway.service;
//
import com.example.apigateway.dto.request.AuthenticationRequest;
import com.example.apigateway.dto.request.IntrospectRequest;
import com.example.apigateway.dto.response.AuthenticationResponse;
import com.example.apigateway.dto.response.IntrospectResponse;
import com.example.apigateway.dto.response.UsersDTO;
//import com.nimbusds.jose.*;
//import com.nimbusds.jose.crypto.MACSigner;
//import com.nimbusds.jose.crypto.MACVerifier;
//import com.nimbusds.jwt.JWTClaimsSet;
//import com.nimbusds.jwt.SignedJWT;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.security.Key;
import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

//
//;
//
@Service
public class AuthenticationService {
//
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    private static final long tokenExpTime = 60 * 60 * 1000; // 1 hour

    public AuthenticationResponse authenticate(AuthenticationRequest request){
        var user = mockGetUser();

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
//
    UsersDTO mockGetUser() {
        UsersDTO usersDTO = new UsersDTO();
        usersDTO.setUserName("anhtht");
        usersDTO.setPassword("$2a$10$Q9TC0nJ5oCLr5JemEi593e07X.F.mJTGtNpcEHboWccDtOl6o3juu");
        return usersDTO;
    }
}
