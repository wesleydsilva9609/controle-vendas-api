package br.com.controledevendas.estoque.controller;

import br.com.controledevendas.estoque.dto.DadosCadastroVenda;
import br.com.controledevendas.estoque.entity.Venda;
import br.com.controledevendas.estoque.repository.VendaRepository;
import br.com.controledevendas.estoque.service.VendaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

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
}
