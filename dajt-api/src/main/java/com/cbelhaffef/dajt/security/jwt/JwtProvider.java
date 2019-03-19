package com.cbelhaffef.dajt.security.jwt;

import com.cbelhaffef.dajt.security.service.UserPrinciple;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {

    private static final Logger LOG = LoggerFactory.getLogger(JwtProvider.class);

    private static final long VALIDITY_TIME_MS_LONG = 10 * 24 * 60 * 60 * 1000; // 10 days Validity
    private static final long VALIDITY_TIME_MS_SHORT =  2 * 60 * 60 * 1000; // 2 hours  validity

    @Value("jwt.secret.key")
    private String jwtSecret;

    @Value("#{new Integer('${jwt.expiration}')}")
    private Integer jwtExpiration;

    public String generateJwtToken(Authentication authentication) {

        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(userPrinciple.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpiration*1000))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                
                .compact();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            LOG.error("Invalid JWT signature -> Message: {} ", e);
        } catch (MalformedJwtException e) {
            LOG.error("Invalid JWT token -> Message: {}", e);
        } catch (ExpiredJwtException e) {
            LOG.error("Expired JWT token -> Message: {}", e);
        } catch (UnsupportedJwtException e) {
            LOG.error("Unsupported JWT token -> Message: {}", e);
        } catch (IllegalArgumentException e) {
            LOG.error("JWT claims string is empty -> Message: {}", e);
        }

        return false;
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody().getSubject();
    }
}
