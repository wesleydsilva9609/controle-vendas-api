package br.com.controledevendas.estoque.entity.validacao;

import br.com.controledevendas.estoque.dto.vendas.DadosCadastroVenda;
import br.com.controledevendas.estoque.infra.exceptions.ValidacaoExeception;
import br.com.controledevendas.estoque.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorQuantideEstoque implements ValidadorDeVendas {
    @Autowired
    private ProdutoRepository produtoRepository;

    @Override
    public void validar(DadosCadastroVenda dadosCadastroVenda) {
     var produto = produtoRepository.findById(dadosCadastroVenda.idproduto()).orElseThrow(() -> new RuntimeException("produto não encontrado"));

     if(produto.getQuantidadeEstoque() < dadosCadastroVenda.quantidade()){
         throw new ValidacaoExeception("estoque insuficiente para a venda desse produto");
     }



    }
}
