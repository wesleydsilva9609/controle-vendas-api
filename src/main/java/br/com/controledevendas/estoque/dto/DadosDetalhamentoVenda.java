package br.com.controledevendas.estoque.dto;

import br.com.controledevendas.estoque.entity.Produto;
import br.com.controledevendas.estoque.entity.Venda;

import java.time.LocalDate;

public record DadosDetalhamentoVenda(Long id, String nomeproduto, int quantidade, LocalDate dataVenda) {
    public DadosDetalhamentoVenda(Venda venda) {
        this(venda.getId(), venda.getProduto().getNome(), venda.getQuantidadeVendida(), venda.getDataVenda());
    }
}
