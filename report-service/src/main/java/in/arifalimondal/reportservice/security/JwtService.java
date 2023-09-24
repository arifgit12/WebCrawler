package in.arifalimondal.reportservice.security;

import in.arifalimondal.reportservice.Dto.ClaimDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

@Component
public class JwtService {

    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

    private static final Logger jwtLogger = LoggerFactory.getLogger(JwtService.class);
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public ClaimDTO extractClaimDetails(String token) {
        try {
            ClaimDTO tokenDetail = new ClaimDTO();
            Claims claims = extractAllClaims(token);
            String username = claims.getSubject();
            List<Object> authoritiesList = (List<Object>) claims.get("authorities");

            Set<GrantedAuthority> authoritiesSet = new HashSet<>();

            for (Object authorityObject : authoritiesList) {
                if (authorityObject instanceof String) {
                    // If the authority is represented as a String, add it as a SimpleGrantedAuthority
                    authoritiesSet.add(new SimpleGrantedAuthority((String) authorityObject));
                } else if (authorityObject instanceof java.util.Map) {
                    // If the authority is represented as a Map (e.g., JSON object), extract the "authority" field
                    Object authorityFieldValue = ((java.util.Map) authorityObject).get("authority");
                    if (authorityFieldValue instanceof String) {
                        authoritiesSet.add(new SimpleGrantedAuthority((String) authorityFieldValue));
                    }
                }
            }

            tokenDetail.setUsername(username);
            tokenDetail.setAuthorities(authoritiesSet);

            return  tokenDetail;
        } catch (MalformedJwtException e) {
            jwtLogger.error(e.getMessage());
            throw e;
        }

    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public boolean validateToken(final String token, final String userLogin) {
        final String username = extractUsername(token);
        return username.equals(userLogin) && !isTokenExpired(token);
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

}
