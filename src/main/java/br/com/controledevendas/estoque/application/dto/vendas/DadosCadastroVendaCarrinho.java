package br.com.controledevendas.estoque.application.dto.vendas;

import java.time.LocalDate;
import java.util.List;

public record DadosCadastroVendaCarrinho(LocalDate dataVenda, List<ItensVendaCarrinho> itens) {
}
