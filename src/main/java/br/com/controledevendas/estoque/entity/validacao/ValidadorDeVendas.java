package br.com.controledevendas.estoque.entity.validacao;

import br.com.controledevendas.estoque.dto.DadosCadastroVenda;
import br.com.controledevendas.estoque.entity.Venda;

public interface ValidadorDeVendas {
    void validar(DadosCadastroVenda dadosCadastroVenda);
}
