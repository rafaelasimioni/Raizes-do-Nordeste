package br.com.rafaelasimioni.raizesdonordeste.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class TokenService {

    @Value("${jwt.secret}")
    private String secret;

    public String gerarToken(UserDetails usuario) {

        Algorithm algoritmo = Algorithm.HMAC256(secret);

        return JWT.create()
                .withIssuer("raizes-do-nordeste")
                .withSubject(usuario.getUsername())
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plus(2, ChronoUnit.HOURS))
                .sign(algoritmo);
    }

    public String validarToken(String token) {

        try {
            Algorithm algoritmo = Algorithm.HMAC256(secret);

            return JWT.require(algoritmo)
                    .withIssuer("raizes-do-nordeste")
                    .build()
                    .verify(token)
                    .getSubject();

        } catch (JWTVerificationException exception) {
            return null;
        }
    }
}