package br.com.controledevendas.estoque.entity;

import br.com.controledevendas.estoque.dto.DadosCadastroVenda;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
public class Venda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Produto produto;
    private int quantidadeVendida;
    private LocalDate dataVenda;

    public Venda(DadosCadastroVenda dadosCadastroVenda) {
        this.id = dadosCadastroVenda.id();
        this.produto = getProduto();
        this.quantidadeVendida = dadosCadastroVenda.quantidade();
        this.dataVenda = dadosCadastroVenda.dataVenda();
    }
}
