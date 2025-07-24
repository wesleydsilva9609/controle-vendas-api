package br.com.controledevendas.estoque.domain.model;

import br.com.controledevendas.estoque.application.dto.produto.DadosCadastroProduto;
import br.com.controledevendas.estoque.application.dto.vendas.DadosCadastroVenda;
import br.com.controledevendas.estoque.application.dto.vendas.DadosVendaAtualizada;
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

    @Test
    void deveriaAtualizarUmaVenda() {
        Produto produto = new Produto(new DadosCadastroProduto("monitor",new BigDecimal("200.00"),10));

        DadosCadastroVenda dadosCadastroVenda = new DadosCadastroVenda(produto.getId(), 5,LocalDate.of(2025,7,23));
        Venda venda = new Venda(dadosCadastroVenda, produto);

        DadosVendaAtualizada dados = new DadosVendaAtualizada(venda.getId(), dadosCadastroVenda.idproduto(), 10, dadosCadastroVenda.dataVenda());

        venda.atualizar(dados,produto);


        Assertions.assertEquals(produto, venda.getProduto());
        Assertions.assertEquals(10, venda.getQuantidadeVendida());
        Assertions.assertEquals(LocalDate.of(2025, 7, 23), venda.getDataVenda());
    }

    @Test
    void deveriaAtualizarSomenteQuantidadeDaVenda() {
        Produto produto = new Produto(new DadosCadastroProduto("teclado", new BigDecimal("150.00"), 20));
        Venda venda = new Venda(new DadosCadastroVenda(produto.getId(), 5, LocalDate.of(2025, 7, 20)), produto);

        DadosVendaAtualizada dadosAtualizados = new DadosVendaAtualizada(
                venda.getId(),
                produto.getId(),
                10,
                null // não alteramos a data
        );

        venda.atualizar(dadosAtualizados, produto);

        Assertions.assertEquals(produto, venda.getProduto());
        Assertions.assertEquals(10, venda.getQuantidadeVendida());
        Assertions.assertEquals(LocalDate.of(2025, 7, 20), venda.getDataVenda()); // data permanece igual
    }
}