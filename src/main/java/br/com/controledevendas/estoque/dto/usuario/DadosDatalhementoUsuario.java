package br.com.controledevendas.estoque.dto.usuario;

import br.com.controledevendas.estoque.entity.Usuario;

public record DadosDatalhementoUsuario(String email, String senha, String role) {
    public DadosDatalhementoUsuario(Usuario usuario){
        this(usuario.getEmail(), usuario.getSenha(), usuario.getRole());
    }
}
