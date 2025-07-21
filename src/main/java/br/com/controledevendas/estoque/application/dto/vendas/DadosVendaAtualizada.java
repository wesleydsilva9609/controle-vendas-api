package br.com.controledevendas.estoque.application.dto.vendas;

import br.com.controledevendas.estoque.domain.model.Venda;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record DadosVendaAtualizada(@NotNull Long id,  Long idproduto,  Integer quantidade, LocalDate dataVenda) {
    public DadosVendaAtualizada(Venda venda){
        this(venda.getId(), venda.getProduto().getId(), venda.getQuantidadeVendida(), venda.getDataVenda());
    }
}
