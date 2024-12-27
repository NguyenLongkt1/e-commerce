package com.example.apigateway.service;
;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

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
//            return null;
//        }
//
//        if (invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID()))
//            return null;
//
//        return signedJWT;
//    }
}
