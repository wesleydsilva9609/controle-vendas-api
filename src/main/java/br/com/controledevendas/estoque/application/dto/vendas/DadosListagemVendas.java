package br.com.controledevendas.estoque.application.dto.vendas;

import br.com.controledevendas.estoque.domain.model.Venda;

import java.time.LocalDate;

public record DadosListagemVendas(Long id, Long idProduto, String nomeProduto, int quantidade, LocalDate dataVenda) {
    public DadosListagemVendas(Venda venda) {
        this(
                venda.getId(),
                venda.getProduto() != null ? venda.getProduto().getId() : null,
                venda.getProduto() != null ? venda.getProduto().getNome() : null,
                venda.getQuantidadeVendida(),
                venda.getDataVenda()
        );
    }
}