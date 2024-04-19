package ec.sasf.sasfpruebamelinamacias.config.Auth;

import ec.sasf.sasfpruebamelinamacias.entity.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Service
public class JwtUtil {
    private final String secretKey ;
    private final long expirationTime ;

    public JwtUtil(@Value("${value}")String key, @Value("${expiration}")String time){
        this.secretKey = key;
        this.expirationTime = convertExpirationTime(time);

    }

    public String extractUsername(String token){
        System.out.println(extractAllClaims(token));
        return extractAllClaims(token).getSubject();

    }
    public <T> T extractClaim(Function<Claims,T> claimsResolver, String token){
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }


    public Claims extractAllClaims(String token){

        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

    }

    public String generatedToken(Usuario user){
        return generatedToken(user, new HashMap<>());
    }

    public String generatedToken(Usuario user, Map<String, Object> claims){
        return createToken(user, claims);
    }

    public String createToken(Usuario user, Map<String,Object> claims){
        claims = Jwts.claims().setSubject(user.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .setIssuedAt(new Date(System.currentTimeMillis()));
        claims.put("roles", user.getAuthorities());
        claims.put("id", user.getId());
        claims.put("sub", user.getEmail());

        return Jwts.builder()
                .setClaims(claims)
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }



    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    public Date expirationDate(String token){
        return extractClaim(Claims::getExpiration, token);
    }

    public Boolean isTokenExpirated(String token){
        return expirationDate(token).before(new Date());
    }

    public Boolean isTokenValid(String token, UserDetails user){
        String username = extractUsername(token);
        return (username.equals(user.getUsername()) && !isTokenExpirated(token));
    }

    private long convertExpirationTime(String time){
        long minutos = Long.parseLong(time);
        return minutos*60000;

    }

}
