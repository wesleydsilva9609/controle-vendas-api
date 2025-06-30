package br.com.controledevendas.estoque.dto;

import jakarta.validation.constraints.NotBlank;

public record DadosLogin(@NotBlank String email,@NotBlank String senha) {
}
