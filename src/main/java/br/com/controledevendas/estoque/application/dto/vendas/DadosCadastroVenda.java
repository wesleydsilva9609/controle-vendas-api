package br.com.controledevendas.estoque.application.dto.vendas;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
public record DadosCadastroVenda(@NotNull Long idproduto, @NotNull Integer quantidade,@FutureOrPresent LocalDate dataVenda)  {
}
