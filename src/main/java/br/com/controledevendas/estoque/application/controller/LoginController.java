package br.com.controledevendas.estoque.application.controller;

import br.com.controledevendas.estoque.application.dto.usuario.DadosCadastroUsuario;
import br.com.controledevendas.estoque.application.dto.usuario.DadosLogin;
import br.com.controledevendas.estoque.domain.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "Login para que possa acessar os EndPoint", description = "Retorna um autenticador ao logar podendo acessar os demais Endpoints")
    public ResponseEntity login(@RequestBody @Valid DadosLogin dadosLogin) {
        return usuarioService.login(dadosLogin);
    }

    @PostMapping("/cadastro")
    @Transactional
    @Operation(summary = "Cadastra um novo usuario", description = "Apenas um ADMIN consegue cadastrar novos usuarios")
    public ResponseEntity cadastrarUsuario(UriComponentsBuilder uriComponentsBuilder, @RequestBody @Valid DadosCadastroUsuario dadosCadastroUsuario) {
        return usuarioService.cadastro(uriComponentsBuilder,dadosCadastroUsuario);
    }
}
