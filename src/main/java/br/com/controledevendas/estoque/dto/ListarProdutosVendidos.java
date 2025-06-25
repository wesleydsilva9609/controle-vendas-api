package br.com.controledevendas.estoque.dto;

import br.com.controledevendas.estoque.entity.Produto;

public record ListarProdutosVendidos(Long idProduto, String nome, Integer quantidadeVendida) {
    public ListarProdutosVendidos(Produto produto) {
        this(produto.getId(), produto.getNome(), produto.getQuantidadeVendida());
    }
}
