package br.com.controledevendas.estoque.domain.service;

import br.com.controledevendas.estoque.application.dto.vendas.*;
import br.com.controledevendas.estoque.domain.model.Venda;
import br.com.controledevendas.estoque.domain.validacao.ValidadorDeVendas;
import br.com.controledevendas.estoque.domain.repository.ProdutoRepository;
import br.com.controledevendas.estoque.domain.repository.VendaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
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
        var produto = produtoRepository.findById(dadosCadastroVenda.idproduto())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));

        // Validação de regras de negócio
        validadorDeVendas.forEach(validador -> validador.validar(dadosCadastroVenda));

        // Atualiza o estoque
        produto.estoque(dadosCadastroVenda.quantidade());

        // Cria venda
        var venda = new Venda(dadosCadastroVenda, produto);

        vendaRepository.save(venda);
        produtoRepository.save(produto); // salva o novo estoque

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

    public ResponseEntity atualizar(DadosVendaAtualizada dadosVendaAtualizada) {
        if(dadosVendaAtualizada.id() == null || !vendaRepository.existsById(Math.toIntExact(dadosVendaAtualizada.id()))){
            return ResponseEntity.notFound().build();
        }

        var venda = vendaRepository.getReferenceById(Math.toIntExact(dadosVendaAtualizada.id()));
        //pega o produto e verifica a quantidade antiga com a quantidade nova para atualizar o estoque
        var produto = produtoRepository.findById((long) Math.toIntExact(dadosVendaAtualizada.idproduto()))
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));
        var quantidadeAntiga = venda.getQuantidadeVendida();
        var quantidadeNova = dadosVendaAtualizada.quantidade();

        produto.atualizarEstoqueEVenda(quantidadeAntiga, quantidadeNova);
        venda.atualizar(dadosVendaAtualizada,produto);

        produtoRepository.save(produto);
        vendaRepository.save(venda);
        venda.setProduto(produto);


        return ResponseEntity.ok().body(new DadosDetalhamentoVenda(venda));
    }

    public ResponseEntity deletarPorId(Long id) {
        if(id == null || !vendaRepository.existsById(id.intValue())){

            return ResponseEntity.notFound().build();
        }
        //busca o id da venda para poder atualizar o estoque
        var venda = vendaRepository.getReferenceById(id.intValue());
        var produto = venda.getProduto();
        produto.devolucao(venda.getQuantidadeVendida());
        //salve a quantidade devolvida ao estoque do produto e depois deleta
        produtoRepository.save(produto);
        vendaRepository.deleteById(Math.toIntExact(id));

        return ResponseEntity.noContent().build();
    }


    public ResponseEntity buscarVendaId(Long id) {
        if(id == null || !vendaRepository.existsById(id.intValue())){
            return ResponseEntity.notFound().build();
        }
        var venda = vendaRepository.getReferenceById(id.intValue());
        return ResponseEntity.ok().body(new DadosDetalhamentoVenda(venda));
    }
}
