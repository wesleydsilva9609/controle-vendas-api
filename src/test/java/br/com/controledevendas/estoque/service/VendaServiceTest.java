package br.com.controledevendas.estoque.service;

import br.com.controledevendas.estoque.application.dto.produto.DadosCadastroProduto;
import br.com.controledevendas.estoque.application.dto.vendas.DadosCadastroVenda;
import br.com.controledevendas.estoque.domain.model.Produto;
import br.com.controledevendas.estoque.domain.model.Venda;
import br.com.controledevendas.estoque.domain.service.VendaService;
import br.com.controledevendas.estoque.domain.validacao.ValidadorDeVendas;
import br.com.controledevendas.estoque.domain.repository.ProdutoRepository;
import br.com.controledevendas.estoque.domain.repository.VendaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class VendaServiceTest {
    @Mock
    private VendaRepository vendaRepository;
    @Mock
    private ProdutoRepository produtoRepository;
    @Mock
    private List<ValidadorDeVendas> validadorDeVendas;
    @InjectMocks
    private VendaService vendaService;
    @Mock
    private UriComponentsBuilder uriComponentsBuilder;

    @Test
    void deveriaLancarExcecaoQuandoProdutoNaoExistir() {
        Mockito.when(produtoRepository.findById(1L)).thenReturn(Optional.empty());
        DadosCadastroVenda dados = new DadosCadastroVenda(1L, 5, LocalDate.now());

        Assertions.assertThrows(ResponseStatusException.class, () ->
                vendaService.cadastrar(uriComponentsBuilder, dados)
        );


    }
}