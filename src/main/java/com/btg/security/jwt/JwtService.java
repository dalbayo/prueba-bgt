package com.btg.security.jwt;
 

//Imports de la librerÃƒÂ­a JJWT (io.jsonwebtoken)
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

//Imports de Spring Security
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

//Java Utilities
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

@Service
public class JwtService {

    // Esta clave debe venir de tus variables de entorno por seguridad
	private static final String SECRET_KEY = "cO92JkZ+9v1Lh3R5sT/p1Xq8N9m7A2z5B8vK9wE3f0o=";
	
    // 10 minutos en milisegundos
    private static final long ACCESS_TOKEN_VALIDITY = 10 * 60 * 1000; 

    public String generateAccessToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        // Puedes agregar roles aquÃƒÂ­
        claims.put("roles", userDetails.getAuthorities()); 

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken() {
        // El Refresh Token suele ser un UUID aleatorio almacenado en SQL Server
        return UUID.randomUUID().toString();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
