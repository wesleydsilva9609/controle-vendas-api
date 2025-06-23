package br.com.controledevendas.estoque.entity;


import br.com.controledevendas.estoque.dto.DadosCadastroProduto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private int quantidadeEstoque;


    public Produto(DadosCadastroProduto dadosCadastroProduto) {
        this.id = dadosCadastroProduto.id();
        this.nome = dadosCadastroProduto.nome();
        this.quantidadeEstoque = dadosCadastroProduto.quantidade();
    }
}
