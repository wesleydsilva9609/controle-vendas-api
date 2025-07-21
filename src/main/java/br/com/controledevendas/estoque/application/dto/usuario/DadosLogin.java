package br.com.controledevendas.estoque.application.dto.usuario;

import jakarta.validation.constraints.NotBlank;

public record DadosLogin(@NotBlank String email,@NotBlank String senha) {
}
