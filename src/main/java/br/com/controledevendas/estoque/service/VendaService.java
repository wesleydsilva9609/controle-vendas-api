package br.com.controledevendas.estoque.service;

import br.com.controledevendas.estoque.dto.DadosCadastroVenda;
import br.com.controledevendas.estoque.dto.DadosDetalhamentoVenda;
import br.com.controledevendas.estoque.entity.Venda;
import br.com.controledevendas.estoque.repository.ProdutoRepository;
import br.com.controledevendas.estoque.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class VendaService {
    @Autowired
    private VendaRepository vendaRepository;
    @Autowired
    private ProdutoRepository produtoRepository;

    public ResponseEntity cadastrar(UriComponentsBuilder uriComponentsBuilder, DadosCadastroVenda dadosCadastroVenda) {
        var produto = produtoRepository.getReferenceById(dadosCadastroVenda.idproduto());
        var venda = new Venda(dadosCadastroVenda.id(), produto, dadosCadastroVenda.quantidade(), dadosCadastroVenda.dataVenda());
        produto.estoque(dadosCadastroVenda.quantidade());
        var uri = uriComponentsBuilder.path("/vendas/{id}").buildAndExpand(venda.getId()).toUri();
        vendaRepository.save(venda);
        produtoRepository.save(produto);

        return ResponseEntity.created(uri).body(new DadosDetalhamentoVenda(venda));
    }


}
