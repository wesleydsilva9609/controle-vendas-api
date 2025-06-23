package br.com.controledevendas.estoque.dto;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizarProduto(
        @NotNull
        Long id,
        String nome,
        int quantidade
) {
}
