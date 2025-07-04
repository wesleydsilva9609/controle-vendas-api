package br.com.controledevendas.estoque.controller;

import br.com.controledevendas.estoque.dto.DadosAtualizarProduto;
import br.com.controledevendas.estoque.dto.DadosCadastroProduto;
import br.com.controledevendas.estoque.dto.DadosListagemProduto;
import br.com.controledevendas.estoque.entity.Produto;
import br.com.controledevendas.estoque.service.ProdutoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/produtos")
@SecurityRequirement(name = "bearer-key")
public class ProdutoController {
    @Autowired
    private ProdutoService produtoService;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrarProduto(UriComponentsBuilder uriComponentsBuilder,@RequestBody @Valid DadosCadastroProduto dadosCadastroProduto) {
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

    @PutMapping
    @Transactional
    public ResponseEntity atualizarProduto(@RequestBody DadosAtualizarProduto dadosAtualizarProdutoproduto){
        return produtoService.atualizarProduto(dadosAtualizarProdutoproduto);
    }

    @GetMapping(params = "nome")
   public ResponseEntity<List<DadosListagemProduto>> listarProdutosPorNome(@RequestParam(name = "nome", required = false) String nome){
        return produtoService.buscarProdutoPorNome(nome);
    }

}
