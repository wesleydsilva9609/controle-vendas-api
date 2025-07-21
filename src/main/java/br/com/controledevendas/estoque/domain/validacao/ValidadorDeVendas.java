package br.com.controledevendas.estoque.domain.validacao;

import br.com.controledevendas.estoque.application.dto.vendas.DadosCadastroVenda;

public interface ValidadorDeVendas {
    void validar(DadosCadastroVenda dadosCadastroVenda);
}
