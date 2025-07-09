package br.com.controledevendas.estoque.entity;

import br.com.controledevendas.estoque.dto.vendas.DadosCadastroVenda;
import br.com.controledevendas.estoque.dto.vendas.DadosVendaAtualizada;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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
    private Integer quantidadeVendida;
    private LocalDate dataVenda;

    public Venda(DadosCadastroVenda dadosCadastroVenda) {
        this.produto = getProduto();
        this.quantidadeVendida = dadosCadastroVenda.quantidade();
        this.dataVenda = dadosCadastroVenda.dataVenda();
    }


    public Venda(Produto produto, Integer quantidade, LocalDate localDate) {
        this.produto = produto;
        this.quantidadeVendida = quantidade;
        this.dataVenda = localDate;
    }

    public void atualizar(DadosVendaAtualizada dadosVendaAtualizada) {
        if(dadosVendaAtualizada.idproduto() != null){
            this.produto = getProduto();
        }if(dadosVendaAtualizada.quantidade() != null){
            this.quantidadeVendida = dadosVendaAtualizada.quantidade();
        }if(dadosVendaAtualizada.dataVenda() != null){
            this.dataVenda = dadosVendaAtualizada.dataVenda();
        }
    }
}
