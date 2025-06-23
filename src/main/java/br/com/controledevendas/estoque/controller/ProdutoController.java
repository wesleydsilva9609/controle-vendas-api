package br.com.controledevendas.estoque.controller;

import br.com.controledevendas.estoque.dto.DadosCadastroProduto;
import br.com.controledevendas.estoque.dto.DadosListagemProduto;
import br.com.controledevendas.estoque.entity.Produto;
import br.com.controledevendas.estoque.service.ProdutoService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
    @Autowired
    private ProdutoService produtoService;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrarProduto(UriComponentsBuilder uriComponentsBuilder,@RequestBody DadosCadastroProduto dadosCadastroProduto) {
        return produtoService.cadastrar(uriComponentsBuilder,dadosCadastroProduto);
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemProduto>> listarProdutos(@PageableDefault(sort = "id", size = 10)Pageable pageable) {
        return produtoService.listar(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity buscarProdutoPorId(@PathVariable Long id){
        return produtoService.buscarPorID(id);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluirProduto(@PathVariable Long id){
        return produtoService.deletarPorId(id);
    }

}
