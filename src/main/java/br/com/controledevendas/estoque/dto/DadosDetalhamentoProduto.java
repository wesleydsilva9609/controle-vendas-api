package br.com.controledevendas.estoque.dto;

import br.com.controledevendas.estoque.entity.Produto;

import java.math.BigDecimal;

public record DadosDetalhamentoProduto(Long id, String nome, BigDecimal preco, int quantidade) {
    public DadosDetalhamentoProduto(Produto produto){
        this(produto.getId(), produto.getNome(),produto.getPreco(), produto.getQuantidadeEstoque());
    }
}
