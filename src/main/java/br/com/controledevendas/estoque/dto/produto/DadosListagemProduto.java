package br.com.controledevendas.estoque.dto.produto;

import br.com.controledevendas.estoque.entity.Produto;

import java.math.BigDecimal;

public record DadosListagemProduto(Long id, String nome, BigDecimal preco, int quantidade) {

    public DadosListagemProduto(Produto produto) {
        this(produto.getId(), produto.getNome(), produto.getPreco(), produto.getQuantidadeEstoque());
    }
}
