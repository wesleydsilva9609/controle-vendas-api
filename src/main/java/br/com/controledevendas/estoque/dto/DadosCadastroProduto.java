package br.com.controledevendas.estoque.dto;

import java.math.BigDecimal;

public record DadosCadastroProduto(String nome, BigDecimal preco, int quantidade) {
}
