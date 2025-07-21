package br.com.controledevendas.estoque.application.dto.vendas;

import br.com.controledevendas.estoque.domain.model.Produto;

public record ListarProdutosVendidos(Long idProduto, String nome, Integer quantidadeVendida) {
    public ListarProdutosVendidos(Produto produto) {
        this(produto.getId(), produto.getNome(), produto.getQuantidadeVendida());
    }
}
