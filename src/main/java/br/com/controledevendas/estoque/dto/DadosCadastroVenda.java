package br.com.controledevendas.estoque.dto;

import br.com.controledevendas.estoque.entity.Produto;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
public record DadosCadastroVenda(@NotNull Long idproduto, @NotNull Integer quantidade,@FutureOrPresent LocalDate dataVenda)  {
}
