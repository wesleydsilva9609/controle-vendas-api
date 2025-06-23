package br.com.controledevendas.estoque.controller;

import br.com.controledevendas.estoque.dto.DadosCadastroProduto;
import br.com.controledevendas.estoque.service.ProdutoService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

}
