package br.com.controledevendas.estoque.application.dto.usuario;

import br.com.controledevendas.estoque.domain.model.Usuario;

public record DadosDatalhementoUsuario(String email, String senha, String role) {
    public DadosDatalhementoUsuario(Usuario usuario){
        this(usuario.getEmail(), usuario.getSenha(), usuario.getRole());
    }
}
