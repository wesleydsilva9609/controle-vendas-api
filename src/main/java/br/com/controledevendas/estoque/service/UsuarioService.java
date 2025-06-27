package br.com.controledevendas.estoque.service;

import br.com.controledevendas.estoque.dto.DadosJwt;
import br.com.controledevendas.estoque.dto.DadosLogin;
import br.com.controledevendas.estoque.entity.Usuario;
import br.com.controledevendas.estoque.repository.UsuarioRepository;
import org.antlr.v4.runtime.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;


    public ResponseEntity login(DadosLogin dadosLogin) {
        var token = new UsernamePasswordAuthenticationToken(dadosLogin.email(),dadosLogin.senha());
        var authentication = authenticationManager.authenticate(token);
        var tokenJWT = tokenService.getToken((Usuario) authentication.getPrincipal());

        return ResponseEntity.ok(new DadosJwt(tokenJWT));
    }



}
