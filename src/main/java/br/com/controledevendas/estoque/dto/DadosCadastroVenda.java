package br.com.controledevendas.estoque.dto;

import br.com.controledevendas.estoque.entity.Produto;
import java.time.LocalDate;
public record DadosCadastroVenda(Long id, Long idproduto, int quantidade, LocalDate dataVenda)  {
}
