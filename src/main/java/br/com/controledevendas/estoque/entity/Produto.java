package br.com.controledevendas.estoque.entity;


import br.com.controledevendas.estoque.dto.DadosAtualizarProduto;
import br.com.controledevendas.estoque.dto.DadosCadastroProduto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@Table(name = "produtos")
@Getter
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


    public Produto(DadosCadastroProduto dadosCadastroProduto) {
        this.id = dadosCadastroProduto.id();
        this.nome = dadosCadastroProduto.nome();
        this.preco = dadosCadastroProduto.preco();
        this.quantidadeEstoque = dadosCadastroProduto.quantidade();
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
    }
}
