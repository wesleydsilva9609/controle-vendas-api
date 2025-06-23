package br.com.controledevendas.estoque.dto;

import br.com.controledevendas.estoque.entity.Produto;

public record DadosDetalhamentoProduto(Long id, String nome, int quantidade) {
    public DadosDetalhamentoProduto(Produto produto){
        this(produto.getId(), produto.getNome(), produto.getQuantidadeEstoque());
    }
}
