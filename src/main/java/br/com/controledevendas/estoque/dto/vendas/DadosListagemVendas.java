package br.com.controledevendas.estoque.dto.vendas;

import br.com.controledevendas.estoque.entity.Venda;

import java.time.LocalDate;

public record DadosListagemVendas(Long id, Long idProduto,String nomeproduto, int quantidade, LocalDate dataVenda) {
    public DadosListagemVendas(Venda venda) {
        this(venda.getId(), venda.getProduto().getId(),venda.getProduto().getNome(), venda.getQuantidadeVendida(), venda.getDataVenda());
    }
}
