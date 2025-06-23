package br.com.controledevendas.estoque.service;

import br.com.controledevendas.estoque.dto.DadosCadastroProduto;
import br.com.controledevendas.estoque.dto.DadosDetalhamentoProduto;
import br.com.controledevendas.estoque.entity.Produto;
import br.com.controledevendas.estoque.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository produtoRepository;


    public ResponseEntity cadastrar(UriComponentsBuilder uriComponentsBuilder, DadosCadastroProduto dadosCadastroProduto){
        var produto = new Produto(dadosCadastroProduto);
        var uri = uriComponentsBuilder.path("/produto/{id}").buildAndExpand(produto.getId()).toUri();
        produtoRepository.save(produto);
        return ResponseEntity.created(uri).body(new DadosDetalhamentoProduto(produto));
    }


}
