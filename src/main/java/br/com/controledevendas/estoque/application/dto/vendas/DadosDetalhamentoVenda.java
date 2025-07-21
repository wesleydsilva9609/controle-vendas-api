package br.com.controledevendas.estoque.application.dto.vendas;

import br.com.controledevendas.estoque.domain.model.Venda;

import java.time.LocalDate;

public record DadosDetalhamentoVenda(Long id, String nomeproduto, int quantidade, LocalDate dataVenda) {
    public DadosDetalhamentoVenda(Venda venda) {
        this(venda.getId(), venda.getProduto().getNome(), venda.getQuantidadeVendida(), venda.getDataVenda());
    }
}
