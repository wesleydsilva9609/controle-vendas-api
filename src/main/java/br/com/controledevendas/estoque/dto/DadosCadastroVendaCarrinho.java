package br.com.controledevendas.estoque.dto;

import java.time.LocalDate;
import java.util.List;

public record DadosCadastroVendaCarrinho(LocalDate dataVenda, List<ItensVendaCarrinho> itens) {
}
