package com.atm.atm_monitoring.util;

import com.atm.atm_monitoring.exceptions.InvalidJwtToken;
import com.atm.atm_monitoring.model.VideoLog;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * Utility class for handling JWT (JSON Web Token) operations such as generating, validating,
 * and parsing JWT tokens in the ATM monitoring system.
 */
@Service
public class JwtUtil {

    /**
     * The secret key used for signing and verifying JWT tokens.
     * Retrieved from the application's properties.
     */
    @Value("${jwt.secretKey}")
    private String jwtSecretKey;

    /**
     * Validates the given JWT token by checking if the username can be extracted
     * and if the token is not expired.
     *
     * @param token the JWT token to validate
     * @return true if the token is valid and not expired, false otherwise
     * @throws InvalidJwtToken if the token is invalid or expired
     */
    public boolean validateAccessToken(String token) throws InvalidJwtToken {
        return getUsername(token) != null && isExpired(token);
    }

    /**
     * Extracts the username (subject) from the given JWT token.
     *
     * @param token the JWT token from which to extract the username
     * @return the username contained in the JWT token
     * @throws InvalidJwtToken if the token cannot be parsed or is invalid
     */
    public String getUsername(String token) throws InvalidJwtToken {
        Claims claims = getClaims(token);
        return claims.getSubject();
    }

    /**
     * Checks if the JWT token has expired by comparing its expiration time with the current time.
     *
     * @param token the JWT token to check for expiration
     * @return true if the token has not expired, false otherwise
     * @throws InvalidJwtToken if the token is invalid or expired
     */
    public boolean isExpired(String token) throws InvalidJwtToken {
        Claims claims = getClaims(token);
        return claims.getExpiration().after(new Date(System.currentTimeMillis()));
    }

    /**
     * Checks if the JWT token has expired by comparing its expiration time with the current time.
     *
     * @param token the JWT token to check for expiration
     * @return {@link Claims}
     * @throws InvalidJwtToken if the token is invalid or expired
     */
    private Claims getClaims(String token) throws InvalidJwtToken {
        try {
            SecretKey secretKey = Keys.hmacShaKeyFor(jwtSecretKey.getBytes());
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

        }
        catch(ExpiredJwtException exception)
        {
            throw new InvalidJwtToken("Token expired");
        }
        catch(SignatureException exception)
        {
            throw new InvalidJwtToken("Invalid token");
        }
        catch (Exception e) {
            throw new InvalidJwtToken("Invalid JWT token. Cannot Parse the token");
        }
    }

    /**
     * Generates a JWT access token for the given employee code for testing
     *
     * @param empCode the employee code for which to generate the access token
     * @return the generated JWT access token
     */
    public String generateAccessToken(String empCode) {
        return Jwts.builder()
                .subject(empCode)
                .issuer("Kinective")
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 48 * 60 * 60 * 1000))
                .signWith(Keys.hmacShaKeyFor(jwtSecretKey.getBytes()))
                .compact();
    }
}

