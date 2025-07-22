package br.com.controledevendas.estoque.domain.model;

import br.com.controledevendas.estoque.application.dto.produto.DadosCadastroProduto;
import br.com.controledevendas.estoque.application.dto.vendas.DadosCadastroVenda;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class VendaTest {

    @Test
    void deveriaCadatrarUmaVenda() {
        Produto produto = new Produto(new DadosCadastroProduto("monitor",new BigDecimal("200.00"),10));

        DadosCadastroVenda dadosCadastroVenda = new DadosCadastroVenda(produto.getId(), 5,LocalDate.of(2025,7,23));

        Venda venda = new Venda(dadosCadastroVenda, produto);


        Assertions.assertEquals(produto, venda.getProduto());
        Assertions.assertEquals(5, venda.getQuantidadeVendida());
        Assertions.assertEquals(LocalDate.of(2025,7,23), venda.getDataVenda());
        Assertions.assertEquals("monitor", produto.getNome());
        Assertions.assertEquals(new BigDecimal("200.00"), produto.getPreco());
        Assertions.assertEquals(10, produto.getQuantidadeEstoque());
    }
}