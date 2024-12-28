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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;
//
//;
//
//@Service
public class AuthenticationService {
//
//    @Value("${jwt.signerKey}")
//    protected String SIGNER_KEY;
//
//    private SignedJWT verifyToken(String token) throws JOSEException, ParseException {
//        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
//
//        SignedJWT signedJWT = SignedJWT.parse(token);
//
//        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
//
//        var verified = signedJWT.verify(verifier);
//
//        if (!(verified && expiryTime.after(new Date()))) {
//            throw new RuntimeException("Invalid JWT");
//        }
//
////        if (invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID()))
////            return null;
//
//        return signedJWT;
//    }
//
//    public IntrospectResponse introspect(IntrospectRequest request)
//            throws JOSEException, ParseException {
//        var token = request.getToken();
//        boolean isValid = true;
//        try {
//            verifyToken(token);
//        } catch (RuntimeException e) {
//            e.printStackTrace();
//            isValid = false;
//        }
//        return new IntrospectResponse(isValid);
//    }
//
    public AuthenticationResponse authenticate(AuthenticationRequest request){
        var user = mockGetUser();

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!authenticated)
            throw new RuntimeException("Invalid password");

        var token = "generateToken(user)";
//
        return new AuthenticationResponse(token, true);
    }
//
//    private String generateToken(UsersDTO user) {
//        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
//
//        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
//                .subject(user.getUserName())
//                .issuer("devteria")
//                .issueTime(new Date())
//                .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
//                .jwtID(UUID.randomUUID().toString())
////                .claim("scope", buildScope(user))
//                .build();
//        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
//
//        JWSObject jwsObject = new JWSObject(header, payload);
//
//        //Kí token
//        try {
//            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
//            return jwsObject.serialize();
//        } catch (JOSEException e) {
//            System.out.println("Cannot create token " + e);
//            throw new RuntimeException(e);
//        }
//    }
//
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
        usersDTO.setPassword("anhtht");
        return usersDTO;
    }
}
