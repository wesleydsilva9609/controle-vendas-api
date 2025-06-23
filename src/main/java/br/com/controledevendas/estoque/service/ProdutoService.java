package br.com.controledevendas.estoque.service;

import br.com.controledevendas.estoque.dto.DadosAtualizarProduto;
import br.com.controledevendas.estoque.dto.DadosCadastroProduto;
import br.com.controledevendas.estoque.dto.DadosDetalhamentoProduto;
import br.com.controledevendas.estoque.dto.DadosListagemProduto;
import br.com.controledevendas.estoque.entity.Produto;
import br.com.controledevendas.estoque.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.List;
import java.util.stream.Collectors;


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


    public ResponseEntity<Page<DadosListagemProduto>> listar(Pageable pageable) {
        var page = produtoRepository.findAll(pageable).map(DadosListagemProduto::new);
        return ResponseEntity.ok(page);
    }

    public List<DadosListagemProduto> conversor(List<Produto> produtoList) {
       return produtoList.stream().map(DadosListagemProduto::new).collect(Collectors.toList());
    }


    public ResponseEntity buscarPorID(Long id){
        var produto = produtoRepository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoProduto(produto));
    }

    public ResponseEntity deletarPorId(Long id) {
        produtoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity atualizarProduto(DadosAtualizarProduto dadosAtualizarProdutoproduto) {
        var produto = produtoRepository.getReferenceById(dadosAtualizarProdutoproduto.id());
        produto.atualizar(dadosAtualizarProdutoproduto);
        return ResponseEntity.ok(new DadosDetalhamentoProduto(produto));
    }
}
