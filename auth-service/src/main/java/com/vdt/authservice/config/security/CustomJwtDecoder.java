package com.vdt.authservice.config.security;

import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CustomJwtDecoder implements JwtDecoder {

  JwtProperties jwtProperties;

  @Override
  public Jwt decode(String token) throws JwtException {
    try {
      SignedJWT signedJWT = SignedJWT.parse(token);

      // Validate the JWT signature
      JWSVerifier verifier = new MACVerifier(jwtProperties.getSecret());
      if (!signedJWT.verify(verifier)) {
        throw new JwtException("Invalid JWT signature");
      }
      // Validate the JWT claims
      Date exp = signedJWT.getJWTClaimsSet().getExpirationTime();
      if (exp != null && exp.before(new Date())) {
        throw new JwtException("JWT token is expired");
      }

      return new Jwt(token,
          signedJWT.getJWTClaimsSet().getIssueTime().toInstant(),
          signedJWT.getJWTClaimsSet().getExpirationTime().toInstant(),
          signedJWT.getHeader().toJSONObject(),
          signedJWT.getJWTClaimsSet().getClaims()
      );

    } catch (Exception e) {
      if(e instanceof JwtException jwtException) {
        throw jwtException;
      }
      throw new JwtException("Invalid token" + e.getMessage());
    }
  }
}
