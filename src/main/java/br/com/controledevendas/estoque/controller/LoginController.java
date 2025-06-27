package br.com.controledevendas.estoque.controller;

import br.com.controledevendas.estoque.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/login")
public class LoginController {
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity login() {
        return usuarioService.login();
    }
}
