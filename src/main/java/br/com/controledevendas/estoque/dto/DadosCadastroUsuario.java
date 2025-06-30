package br.com.controledevendas.estoque.dto;

import br.com.controledevendas.estoque.entity.Usuario;

public record DadosCadastroUsuario(String email, String senha, String role) {
    public DadosCadastroUsuario(Usuario usuario){
        this(usuario.getEmail(), usuario.getSenha(), usuario.getRole());
    }
}
