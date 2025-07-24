package br.com.controledevendas.estoque.application.controller;

import br.com.controledevendas.estoque.application.dto.produto.DadosAtualizarProduto;
import br.com.controledevendas.estoque.application.dto.produto.DadosCadastroProduto;
import br.com.controledevendas.estoque.application.dto.produto.DadosListagemProduto;
import br.com.controledevendas.estoque.domain.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "Cria um novo produto", description = "Endpoint para cadastrar um novo produto no sistema")
    public ResponseEntity cadastrarProduto(UriComponentsBuilder uriComponentsBuilder,@RequestBody @Valid DadosCadastroProduto dadosCadastroProduto) {
        return produtoService.cadastrar(uriComponentsBuilder,dadosCadastroProduto);
    }

    @GetMapping
    @Operation(summary = "Lista todas os produtos", description = "Retorna uma lista de produtos")
    public ResponseEntity<Page<DadosListagemProduto>> listarProdutos(@PageableDefault(sort = "id", size = 5)Pageable pageable) {
        return produtoService.listar(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um produto por id", description = "Retorna um produto pelo id")
    public ResponseEntity buscarProdutoPorId(@PathVariable Long id){
        return produtoService.buscarPorID(id);
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Deleta uma produto pelo id", description = "Endpoint para deletar um  produto no sistema")
    public ResponseEntity excluirProduto(@PathVariable Long id){
        return produtoService.deletarPorId(id);
    }

    @PutMapping
    @Transactional
    @Operation(summary = "Atualiza um produto", description = "Endpoint para atualizar um  produto no sistema")
    public ResponseEntity atualizarProduto(@RequestBody DadosAtualizarProduto dadosAtualizarProdutoproduto){
        return produtoService.atualizarProduto(dadosAtualizarProdutoproduto);
    }

    @GetMapping(params = "nome")
    @Operation(summary = "Busca produto pelo nome", description = "Retorna produtos pelo nome")
   public ResponseEntity<List<DadosListagemProduto>> listarProdutosPorNome(@RequestParam(name = "nome", required = false) String nome){
        return produtoService.buscarProdutoPorNome(nome);
    }

}
