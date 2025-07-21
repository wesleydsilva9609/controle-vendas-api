package br.com.controledevendas.estoque.domain.model;

import br.com.controledevendas.estoque.application.dto.vendas.DadosCadastroVenda;
import br.com.controledevendas.estoque.application.dto.vendas.DadosVendaAtualizada;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
public class Venda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Produto produto;
    private Integer quantidadeVendida;
    private LocalDate dataVenda;

    public Venda(DadosCadastroVenda dadosCadastroVenda,Produto produto) {
        this.produto = produto;
        this.quantidadeVendida = dadosCadastroVenda.quantidade();
        this.dataVenda = dadosCadastroVenda.dataVenda();
    }


    public Venda(Produto produto, Integer quantidade, LocalDate localDate) {
        this.produto = produto;
        this.quantidadeVendida = quantidade;
        this.dataVenda = localDate;
    }

    public void atualizar(DadosVendaAtualizada dadosVendaAtualizada,Produto produto) {
        if(dadosVendaAtualizada.idproduto() != null){
            this.produto = produto;
        }if(dadosVendaAtualizada.quantidade() != null){
            this.quantidadeVendida = dadosVendaAtualizada.quantidade();
        }if(dadosVendaAtualizada.dataVenda() != null){
            this.dataVenda = dadosVendaAtualizada.dataVenda();
        }
    }
}
