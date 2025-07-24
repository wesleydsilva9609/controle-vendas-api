package br.com.controledevendas.estoque.application.controller;


import br.com.controledevendas.estoque.application.dto.vendas.*;
import br.com.controledevendas.estoque.domain.service.VendaService;
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
@RequestMapping("/vendas")
@SecurityRequirement(name = "bearer-key")
public class VendaController {
    @Autowired
    private VendaService vendaService;


    @PostMapping
    @Transactional
    @Operation(summary = "Cria uma nova venda", description = "Endpoint para cadastrar uma nova venda no sistema")
    public ResponseEntity cadastrar(UriComponentsBuilder uriComponentsBuilder, @RequestBody @Valid DadosCadastroVenda dadosCadastroVenda) {
        return vendaService.cadastrar(uriComponentsBuilder,dadosCadastroVenda);
    }

    @PostMapping("/carrinho")
    @Transactional
    @Operation(summary = "Cria uma nova venda em formato de carrinho ", description = "Endpoint para cadastrar mais de um produto no carrinho de venda ")
    public ResponseEntity cadastrarVendaCarrinho(UriComponentsBuilder uriComponentsBuilder, @RequestBody @Valid DadosCadastroVendaCarrinho dadosCadastroVendaCarrinho) {
        return vendaService.cadastrarcarrinho(uriComponentsBuilder,dadosCadastroVendaCarrinho);
    }


    @GetMapping
    @Operation(summary = "Lista todas as vendas", description = "Retorna todas as vendas Cadastradas no sistema")
    public ResponseEntity<Page<DadosListagemVendas>> listar(@PageableDefault(size = 10) Pageable pageable) {
        return vendaService.listarVendas(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca uma venda por id", description = "Retorna a venda por id")
    public ResponseEntity buscarVendaPorId(@PathVariable Long id){
        return vendaService.buscarVendaId(id);
    }

    @PutMapping
    @Transactional
    @Operation(summary = "Atualiza uma  venda", description = "Endpoint para atualizar alguma venda")
    public ResponseEntity atualizarVenda(@RequestBody @Valid DadosVendaAtualizada dadosVendaAtualizada) {
        return vendaService.atualizar(dadosVendaAtualizada);
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Deleta uma venda pelo id", description = "Endpoint para deletar uma venda no sistema")
    public ResponseEntity deletarVendaId(@PathVariable Long id){
        return vendaService.deletarPorId(id);
    }

    @GetMapping("/{ano}/{mes}")
    @Operation(summary = "Busca uma venda pela data", description = "Endpoint para buscar venda por data cadastrada no sistema")
    public ResponseEntity<List<DadosListagemVendas>> buscarPorAnoMes( @PathVariable String ano,@PathVariable String mes) {
        return vendaService.buscarPorAnoMes(ano,mes);
    }

    @GetMapping("/relatorio")
    @Operation(summary = "Busca um relatorio de vendas", description = "Retorna um relatio de vendas dos produtos cadastrados no sistema")
    public ResponseEntity<Page<ListarProdutosVendidos>> buscarTodosVendas(@PageableDefault(size = 5) Pageable pageable) {
        return vendaService.buscarTodasAsVendas(pageable);
    }
}
