package com.vdt.authservice.config.security;


import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.vdt.authservice.dto.Token;
import com.vdt.authservice.entity.User;
import com.vdt.authservice.exception.AppException;
import com.vdt.authservice.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class JwtHelper {
    JwtProperties jwtProperties;


    public Token createToken(User user) {
        String jwtId = UUID.randomUUID().toString();
        String accessToken = generateToken(user, false, jwtId);
        String refreshToken = generateToken(user, true, jwtId);

        return new Token(accessToken, refreshToken);
    }

    public boolean isExpired(String token) {
        try {

            return getRemainingExpTime(token) > 0;

        } catch (Exception e) {
            return false;
        }
    }

    public long getRemainingExpTime(String token) {
        Date leftTime = (Date) getClaimFromToken(token, "exp");
        return leftTime.getTime() - new Date().getTime();
    }

    public String getSubject(String token) {
        return (String) getClaimFromToken(token, "sub");
    }

    public List<String> getRoles(String token) {
        var roles = getClaimFromToken(token, "roles");
        if (roles instanceof String roleString) {
            return Arrays.asList((roleString).split(" "));
        }
        return List.of();
    }

    private Object getClaimFromToken(String token, String claimName) {
        try {
            JWSObject jwsObject = JWSObject.parse(token);
            JWSVerifier verifier = new MACVerifier(jwtProperties.getSecret());

            if (!jwsObject.verify(verifier)) {
                throw new IllegalArgumentException("Invalid JWT signature");
            }

            JWTClaimsSet claimsSet = JWTClaimsSet.parse(jwsObject.getPayload().toJSONObject());

            return claimsSet.getClaim(claimName);

        } catch (Exception e) {
            throw new AppException(ErrorCode.EXTRACT_TOKEN_FAIL);
        }
    }

    private String getRoleStringFromUser(User user) {
        return StringUtils.collectionToDelimitedString(user.getRoles(), " ");
    }

    private String generateToken(User user, boolean isRefreshToken , String jwtId) {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS256);
        long expTime = jwtProperties.getExpiration() * (isRefreshToken ? 10 : 1);

        JWTClaimsSet jwtClaimSet = new JWTClaimsSet.Builder().subject(user.getId().toString())
                .issuer("VDT")
                .issueTime(
                        new Date())
                .jwtID(jwtId)
                .claim("type", isRefreshToken ? "refresh" : "access")
                .expirationTime(new Date(Instant.now().toEpochMilli() + expTime) )
                .claim("roles", getRoleStringFromUser(user))
                .build();

        JWSObject jwsObject = new JWSObject(jwsHeader, jwtClaimSet.toPayload());

        try {
            jwsObject.sign(new MACSigner(jwtProperties.getSecret()));

        } catch (
                JOSEException e) {
            throw new AppException(ErrorCode.GENERATE_TOKEN_FAIL);
        }
        return jwsObject.serialize();
    }

}
