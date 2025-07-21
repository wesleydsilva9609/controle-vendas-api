package br.com.controledevendas.estoque.domain.model;


import br.com.controledevendas.estoque.application.dto.produto.DadosAtualizarProduto;
import br.com.controledevendas.estoque.application.dto.produto.DadosCadastroProduto;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@Table(name = "produtos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private BigDecimal preco;
    private int quantidadeEstoque;
    private Integer quantidadeVendida;


    public Produto(DadosCadastroProduto dadosCadastroProduto) {
        this.nome = dadosCadastroProduto.nome();
        this.preco = dadosCadastroProduto.preco();
        this.quantidadeEstoque = dadosCadastroProduto.quantidade();
        this.quantidadeVendida = 0;

    }



    public void atualizar(DadosAtualizarProduto dadosAtualizarProdutoproduto) {
        if(dadosAtualizarProdutoproduto.nome() != null){
            this.nome = dadosAtualizarProdutoproduto.nome();
        }
        if(dadosAtualizarProdutoproduto.quantidade() != 0){
            this.quantidadeEstoque = dadosAtualizarProdutoproduto.quantidade();
        }
        if(dadosAtualizarProdutoproduto.preco() != null){
            this.preco = dadosAtualizarProdutoproduto.preco().setScale(2, RoundingMode.HALF_UP);
        }
    }

    public void estoque(int quantidade) {
        quantidadeEstoque -= quantidade;
        quantidadeVendida += quantidade;
    }

    public void atualizarEstoqueEVenda(int quantidadeAntiga, int quantidadeNova) {
        // Reverte a venda anterior
        this.quantidadeEstoque += quantidadeAntiga;
        this.quantidadeVendida -= quantidadeAntiga;

        // Aplica a nova venda
        this.quantidadeEstoque -= quantidadeNova;
        this.quantidadeVendida += quantidadeNova;

        // Proteção contra negativos
        if (this.quantidadeVendida < 0) this.quantidadeVendida = 0;
        if (this.quantidadeEstoque < 0) this.quantidadeEstoque = 0;
    }

    public void devolucao(Integer quantidadeVendida) {
        quantidadeEstoque += quantidadeVendida;
        this.quantidadeVendida -= quantidadeVendida;
    }
}
