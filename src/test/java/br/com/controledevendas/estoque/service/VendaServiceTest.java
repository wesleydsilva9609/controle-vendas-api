package br.com.controledevendas.estoque.service;

import br.com.controledevendas.estoque.entity.validacao.ValidadorDeVendas;
import br.com.controledevendas.estoque.repository.ProdutoRepository;
import br.com.controledevendas.estoque.repository.VendaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void cadastrar() {
    }
}