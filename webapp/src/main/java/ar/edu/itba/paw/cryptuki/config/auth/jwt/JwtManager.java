package ar.edu.itba.paw.cryptuki.config.auth.jwt;

import io.jsonwebtoken.*;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@PropertySource("classpath:application.properties")
public class JwtManager { // Component that implements serializable?

    private static int ACCESS_TOKEN_VALIDITY = 60 * 60; // An hour
    private static int REFRESH_TOKEN_VALIDITY = 24 * 60 * 60; // A day

    private final String secret;
    private final JwtParser parser;

    public JwtManager(String secret) {
        this.secret = secret;
        this.parser = Jwts.parser().setSigningKey(secret);
    }

    public boolean isTokenValid(String token){
        try {
            parser.parseClaimsJws(token);
        } catch (MalformedJwtException | SignatureException | UnsupportedJwtException | ExpiredJwtException e) {
            return false;
        }
        return true;
    }

    public Claims getAllClaimsFromToken(String token) {
        return parser.parseClaimsJws(token).getBody();
    }
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }
    public Date getIssuedAtDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getIssuedAt);
    }
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }
    public String getTypeFromToken(String token) {
        return getClaimFromToken(token, claims -> claims.get("type").toString());
    }
    public boolean isTokenExpired(String token) {
        try {
            parser.parseClaimsJws(token);
        } catch (ExpiredJwtException e) {
            return true;
        }
        return false;
    }

    public String generateAccessToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("type", "access");
        if(userDetails.getAuthorities().stream().findFirst().isPresent())
            claims.put("role",userDetails.getAuthorities().stream().findFirst().get().getAuthority());
        return doGenerateToken(claims, userDetails.getUsername(), ACCESS_TOKEN_VALIDITY);
    }

    public String generateRefreshToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("type", "refresh");
        if(userDetails.getAuthorities().stream().findFirst().isPresent())
            claims.put("role",userDetails.getAuthorities().stream().findFirst().get().getAuthority());
        return doGenerateToken(claims, userDetails.getUsername(), REFRESH_TOKEN_VALIDITY);
    }

    private String doGenerateToken(Map<String, Object> claims, String subject, int validityTimeInSecs) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + validityTimeInSecs * 1000))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
}
