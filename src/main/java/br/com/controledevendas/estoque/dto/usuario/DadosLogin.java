package br.com.controledevendas.estoque.dto.usuario;

import jakarta.validation.constraints.NotBlank;

public record DadosLogin(@NotBlank String email,@NotBlank String senha) {
}
