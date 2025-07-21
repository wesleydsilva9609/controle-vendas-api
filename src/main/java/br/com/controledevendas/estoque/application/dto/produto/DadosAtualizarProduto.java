package br.com.controledevendas.estoque.application.dto.produto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record DadosAtualizarProduto(
        @NotNull
        Long id,
        String nome,
        BigDecimal preco,
        int quantidade
) {
}
