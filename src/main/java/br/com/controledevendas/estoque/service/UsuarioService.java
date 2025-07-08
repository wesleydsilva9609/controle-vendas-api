package br.com.controledevendas.estoque.service;

import br.com.controledevendas.estoque.dto.usuario.DadosCadastroUsuario;
import br.com.controledevendas.estoque.dto.usuario.DadosDatalhementoUsuario;
import br.com.controledevendas.estoque.dto.usuario.DadosJwt;
import br.com.controledevendas.estoque.dto.usuario.DadosLogin;
import br.com.controledevendas.estoque.entity.Usuario;
import br.com.controledevendas.estoque.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public ResponseEntity login(DadosLogin dadosLogin) {
        var token = new UsernamePasswordAuthenticationToken(dadosLogin.email(), dadosLogin.senha());
        var authentication = authenticationManager.authenticate(token);
        var tokenJWT = tokenService.getToken((Usuario) authentication.getPrincipal());
        return ResponseEntity.ok(new DadosJwt(tokenJWT));
    }


    public ResponseEntity cadastro(UriComponentsBuilder uriComponentsBuilder,DadosCadastroUsuario dadosCadastroUsuario) {
        // Criptografar a senha
        String senhaCriptografada = passwordEncoder.encode(dadosCadastroUsuario.senha());

        var usuario = new Usuario(dadosCadastroUsuario.email(), senhaCriptografada, dadosCadastroUsuario.role());
        usuarioRepository.save(usuario);
        var uri = uriComponentsBuilder.path("/usuario").buildAndExpand(usuario.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDatalhementoUsuario(usuario));
    }
}
