package br.com.controledevendas.estoque.service;

import br.com.controledevendas.estoque.dto.DadosCadastroVenda;
import br.com.controledevendas.estoque.dto.DadosDetalhamentoVenda;
import br.com.controledevendas.estoque.dto.DadosListagemVendas;
import br.com.controledevendas.estoque.entity.Venda;
import br.com.controledevendas.estoque.repository.ProdutoRepository;
import br.com.controledevendas.estoque.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class VendaService {
    @Autowired
    private VendaRepository vendaRepository;
    @Autowired
    private ProdutoRepository produtoRepository;

    public ResponseEntity cadastrar(UriComponentsBuilder uriComponentsBuilder, DadosCadastroVenda dadosCadastroVenda) {
        var produto = produtoRepository.getReferenceById(dadosCadastroVenda.idproduto());
        var venda = new Venda(dadosCadastroVenda.id(), produto, dadosCadastroVenda.quantidade(), dadosCadastroVenda.dataVenda());
        produto.estoque(dadosCadastroVenda.quantidade());
        var uri = uriComponentsBuilder.path("/vendas/{id}").buildAndExpand(venda.getId()).toUri();
        vendaRepository.save(venda);
        produtoRepository.save(produto);

        return ResponseEntity.created(uri).body(new DadosDetalhamentoVenda(venda));
    }

    public ResponseEntity<Page<DadosListagemVendas>> listarVendas(Pageable pageable) {
        var page = vendaRepository.findAll(pageable).map(DadosListagemVendas::new);
        return ResponseEntity.ok(page);
    }

    public ResponseEntity<List<DadosListagemVendas>> buscarPorAnoMes(String ano, String mes) {
        if(!ano.matches("\\d{4}") && !mes.matches("\\d{2}") ) {
            return ResponseEntity.badRequest().build();
        }

        int anosrt = Integer.parseInt(ano);
        int mesrt = Integer.parseInt(mes);

        var buscarVenda = vendaRepository.buscarPorData(anosrt,mesrt);

        return ResponseEntity.ok(conversor(buscarVenda));
    }

    public List<DadosListagemVendas> conversor(List<Venda> vendaList){
        return vendaList.stream().map(DadosListagemVendas::new).collect(Collectors.toList());
    }
}
