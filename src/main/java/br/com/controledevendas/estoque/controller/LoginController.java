package br.com.controledevendas.estoque.controller;

import br.com.controledevendas.estoque.dto.usuario.DadosCadastroUsuario;
import br.com.controledevendas.estoque.dto.usuario.DadosLogin;
import br.com.controledevendas.estoque.service.UsuarioService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/Login")
public class LoginController {
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    @Transactional
    public ResponseEntity login(@RequestBody @Valid DadosLogin dadosLogin) {
        return usuarioService.login(dadosLogin);
    }

    @PostMapping("/cadastro")
    @Transactional
    public ResponseEntity cadastrarUsuario(UriComponentsBuilder uriComponentsBuilder, @RequestBody @Valid DadosCadastroUsuario dadosCadastroUsuario) {
        return usuarioService.cadastro(uriComponentsBuilder,dadosCadastroUsuario);
    }
}
