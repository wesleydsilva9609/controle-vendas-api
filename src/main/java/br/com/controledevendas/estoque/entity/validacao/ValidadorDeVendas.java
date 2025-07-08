package br.com.controledevendas.estoque.entity.validacao;

import br.com.controledevendas.estoque.dto.vendas.DadosCadastroVenda;

public interface ValidadorDeVendas {
    void validar(DadosCadastroVenda dadosCadastroVenda);
}
