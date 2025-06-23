package br.com.controledevendas.estoque.dto;

import br.com.controledevendas.estoque.entity.Produto;

public record DadosListagemProduto(Long id, String nome, int quantidade) {

    public DadosListagemProduto(Produto produto) {
        this(produto.getId(), produto.getNome(), produto.getQuantidadeEstoque());
    }
}
