package br.com.controledevendas.estoque.service;

import br.com.controledevendas.estoque.entity.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenService {

    public String getToken(Usuario usuario) {
        try {
            var algorithm = Algorithm.HMAC256("1234");
            return JWT.create().
                    withIssuer("estoque_api")
                    .withClaim("role", usuario.getRole())
                    .withSubject(usuario.getUsername())
                    .withExpiresAt(Dataexpiracao())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException("Erro ao gerar token");
        }
    }

    public String getSubjetct(String token){

        try {
            var algorithm = Algorithm.HMAC256("1234");
            return JWT.require(algorithm)
                    .withIssuer("estoque_api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception){
            throw new RuntimeException("token Invalido");
        }
    }

    private Instant Dataexpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

}
