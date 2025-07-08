package br.com.controledevendas.estoque.service;

import br.com.controledevendas.estoque.dto.vendas.*;
import br.com.controledevendas.estoque.entity.Venda;
import br.com.controledevendas.estoque.entity.validacao.ValidadorDeVendas;
import br.com.controledevendas.estoque.repository.ProdutoRepository;
import br.com.controledevendas.estoque.repository.VendaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendaService {
    @Autowired
    private VendaRepository vendaRepository;
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private List<ValidadorDeVendas> validadorDeVendas;

    public ResponseEntity cadastrar(UriComponentsBuilder uriComponentsBuilder, DadosCadastroVenda dadosCadastroVenda) {
        var produto = produtoRepository.getReferenceById(dadosCadastroVenda.idproduto());

        validadorDeVendas.forEach(validadorDeVendas -> validadorDeVendas.validar(dadosCadastroVenda));

        //atualiza o estoque e a quantidade do intem vendido
        produto.estoque(dadosCadastroVenda.quantidade());

        var venda = new Venda(produto, dadosCadastroVenda.quantidade(), dadosCadastroVenda.dataVenda());

        vendaRepository.save(venda);
        // Salva o produto com o novo estoque
        produtoRepository.save(produto);

        var uri = uriComponentsBuilder.path("/vendas/{id}").buildAndExpand(venda.getId()).toUri();

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

    public ResponseEntity<Page<ListarProdutosVendidos>> buscarTodasAsVendas(@PageableDefault(size = 10) Pageable pageable) {
        var produto = produtoRepository.findAll(pageable).map(ListarProdutosVendidos::new);
        return ResponseEntity.ok(produto);

    }


    public ResponseEntity cadastrarcarrinho(UriComponentsBuilder uriComponentsBuilder, @Valid DadosCadastroVendaCarrinho dadosCadastroVendaCarrinho){
        List<Venda> vendaList = new ArrayList<>();

        for (ItensVendaCarrinho itensVendaCarrinho : dadosCadastroVendaCarrinho.itens()){
            //simula os dados pra poder utilizar o validador
            DadosCadastroVenda dadossimulados = new DadosCadastroVenda(itensVendaCarrinho.idproduto(), itensVendaCarrinho.quantidade(), dadosCadastroVendaCarrinho.dataVenda());

            var produto = produtoRepository.findById(itensVendaCarrinho.idproduto()).orElseThrow(() -> new RuntimeException("produto não encontrado"));
            validadorDeVendas.forEach(validadorDeVendas -> validadorDeVendas.validar(dadossimulados));
            produto.estoque(itensVendaCarrinho.quantidade());
            produtoRepository.save(produto);

            var venda = new Venda(produto, itensVendaCarrinho.quantidade(), dadosCadastroVendaCarrinho.dataVenda());

            vendaRepository.save(venda);

            vendaList.add(venda);

        }
        var uri = uriComponentsBuilder.path("/vendas/carrinho").build().toUri();

        return ResponseEntity.created(uri).body(vendaList.stream().map(DadosDetalhamentoVenda::new).toList());

    }

    public ResponseEntity deletarPorId(Long id) {
        vendaRepository.deleteById(Math.toIntExact(id));
        return ResponseEntity.noContent().build();
    }
}
