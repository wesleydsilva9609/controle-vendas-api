package br.com.controledevendas.estoque.controller;

import br.com.controledevendas.estoque.dto.DadosCadastroVenda;
import br.com.controledevendas.estoque.dto.DadosListagemVendas;
import br.com.controledevendas.estoque.dto.ListarProdutosVendidos;
import br.com.controledevendas.estoque.service.VendaService;
import jakarta.transaction.Transactional;
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
public class VendaController {
    @Autowired
    private VendaService vendaService;


    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(UriComponentsBuilder uriComponentsBuilder, @RequestBody DadosCadastroVenda dadosCadastroVenda) {
        return vendaService.cadastrar(uriComponentsBuilder,dadosCadastroVenda);
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemVendas>> listar(@PageableDefault(size = 10) Pageable pageable) {
        return vendaService.listarVendas(pageable);
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
