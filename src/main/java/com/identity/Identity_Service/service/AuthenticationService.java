package com.identity.Identity_Service.service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.identity.Identity_Service.dto.request.AuthenticationRequest;
import com.identity.Identity_Service.dto.request.IntrospectRequest;
import com.identity.Identity_Service.dto.response.*;
import com.identity.Identity_Service.exceptions.AppException;
import com.identity.Identity_Service.exceptions.ErrorCode;
import com.identity.Identity_Service.repository.UserRepository;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

import java.util.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService{

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;
    UserRepository repo;


    public IntrospectResponse introspect( IntrospectRequest req){
        var token = req.getToken();
        try{
            JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
            SignedJWT signedJWT = SignedJWT.parse(token);
            Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
            var result = signedJWT.verify(verifier);
            return IntrospectResponse.builder().valid(result && expirationTime.after(new Date())).build();

        }catch(JOSEException  | ParseException e){
            System.out.println(e);
            return IntrospectResponse.builder().valid(false).build();
        }
    } 
    public AuthenticationResponse auth(AuthenticationRequest req){
        var user = repo.findByUsername(req.getUsername())
                    .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXIST));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean result = passwordEncoder.matches(req.getPassword(), user.getPassword());
        if(!result){
            throw new AppException(ErrorCode.LOGIN_FAILED);
        }
        var token = genToken(req.getUsername());

        return AuthenticationResponse.builder()
                .token(token)
                .result(true)
                .build();
    }

    private String genToken(String username){
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                                        .subject(username)
                                        .issuer("login")
                                        .issueTime(new Date())
                                        .expirationTime(new Date(
                                                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                                                        ))
                                        .claim("CustomerCalim", "Custom")
                                        .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);
        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            System.out.println("cannot create token");
            throw new RuntimeException();

        }
    }
}
