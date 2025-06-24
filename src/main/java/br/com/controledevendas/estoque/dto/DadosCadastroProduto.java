package br.com.controledevendas.estoque.dto;

import java.math.BigDecimal;

public record DadosCadastroProduto(Long id, String nome, BigDecimal preco, int quantidade) {
}
