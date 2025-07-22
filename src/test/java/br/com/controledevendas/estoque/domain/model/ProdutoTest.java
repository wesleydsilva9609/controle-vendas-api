package br.com.controledevendas.estoque.domain.model;

import br.com.controledevendas.estoque.application.dto.produto.DadosAtualizarProduto;
import br.com.controledevendas.estoque.application.dto.produto.DadosCadastroProduto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProdutoTest {

    @Test
    void deveAtualizarEstoqueEQuantidadeVendidaAoVenderProduto() {
        Produto produto = new Produto();
        produto.setQuantidadeEstoque(10); // estoque atual
        produto.setQuantidadeVendida(5); // vendidos atual

        produto.estoque(5); // quantidade comprada atual

        Assertions.assertEquals(5, produto.getQuantidadeEstoque()); // novo estoque
        Assertions.assertEquals(10, produto.getQuantidadeVendida()); // nova quantidade de produtos vendidos
    }

    @Test
    void DeveriaatualizarEstoqueEVenda(){
        Produto produto = new Produto();
        produto.setQuantidadeEstoque(20); // 20 produtos no estoque
        produto.setQuantidadeVendida(15); // 15 produtos já vendidos

       int quantidadeAntiga = 25; // soma estoque com quantidade antiga, e subtrai qtd vendida pela qtdantiga
       int quantidadeNova = 25; // subtrai a soma do estoque com a qtdnova, soma quantidade antiga com a quantidade novo

        produto.atualizarEstoqueEVenda(quantidadeAntiga, quantidadeNova);

        Assertions.assertEquals(20, produto.getQuantidadeEstoque());
        Assertions.assertEquals(15, produto.getQuantidadeVendida());

    }

    @Test
    void DeveriaDevolverProdutosAoEstoque() {
        Produto produto = new Produto();
        produto.setQuantidadeEstoque(10); // estoque atual
        produto.setQuantidadeVendida(5); // vendido

        produto.devolucao(3); // devolvido retornou ao estoque

        assertEquals(13, produto.getQuantidadeEstoque()); // estoque deve ficar em 13
        assertEquals(2, produto.getQuantidadeVendida()); // quantidade vendida apos devolução

    }

    @Test
    void deveriaCadatrarUmProduto() {
       DadosCadastroProduto dados = new DadosCadastroProduto("monitor",new BigDecimal("200.00"),10);

       Produto produto = new Produto(dados);

        Assertions.assertEquals("monitor", produto.getNome());
        Assertions.assertEquals(new BigDecimal("200.00"), produto.getPreco());
        Assertions.assertEquals(10, produto.getQuantidadeEstoque());
    }

    @Test
    void deveriaAtualizarUmProduto() {
        Produto produto = new Produto(new DadosCadastroProduto("monitor",new BigDecimal("200.00"),10));
        DadosAtualizarProduto atualizarProduto = new DadosAtualizarProduto(produto.getId(),"monitor HD",new BigDecimal("250.00"),10);

        produto.atualizar(atualizarProduto);

        Assertions.assertEquals("monitor HD", produto.getNome());
        Assertions.assertEquals(new BigDecimal("250.00"), produto.getPreco());
        Assertions.assertEquals(10, produto.getQuantidadeEstoque());
    }

}