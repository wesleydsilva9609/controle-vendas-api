package br.com.controledevendas.estoque.controller;

import br.com.controledevendas.estoque.dto.vendas.*;
import br.com.controledevendas.estoque.service.VendaService;
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
    public ResponseEntity cadastrar(UriComponentsBuilder uriComponentsBuilder, @RequestBody @Valid DadosCadastroVenda dadosCadastroVenda) {
        return vendaService.cadastrar(uriComponentsBuilder,dadosCadastroVenda);
    }

    @PostMapping("/carrinho")
    @Transactional
    public ResponseEntity cadastrarVendaCarrinho(UriComponentsBuilder uriComponentsBuilder, @RequestBody @Valid DadosCadastroVendaCarrinho dadosCadastroVendaCarrinho) {
        return vendaService.cadastrarcarrinho(uriComponentsBuilder,dadosCadastroVendaCarrinho);
    }


    @GetMapping
    public ResponseEntity<Page<DadosListagemVendas>> listar(@PageableDefault(size = 10) Pageable pageable) {
        return vendaService.listarVendas(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity buscarVendaPorId(@PathVariable Long id){
        return vendaService.buscarVendaId(id);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizarVenda(@RequestBody @Valid DadosVendaAtualizada dadosVendaAtualizada) {
        return vendaService.atualizar(dadosVendaAtualizada);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deletarVendaId(@PathVariable Long id){
        return vendaService.deletarPorId(id);
    }

    @GetMapping("/{ano}/{mes}")
    public ResponseEntity<List<DadosListagemVendas>> buscarPorAnoMes( @PathVariable String ano,@PathVariable String mes) {
        return vendaService.buscarPorAnoMes(ano,mes);
    }

    @GetMapping("/relatorio")
    public ResponseEntity<Page<ListarProdutosVendidos>> buscarTodosVendas(@PageableDefault(size = 10) Pageable pageable) {
        return vendaService.buscarTodasAsVendas(pageable);
    }
}
