package br.com.controledevendas.estoque.controller;

import br.com.controledevendas.estoque.application.dto.produto.DadosCadastroProduto;
import br.com.controledevendas.estoque.application.dto.vendas.DadosCadastroVenda;
import br.com.controledevendas.estoque.application.dto.vendas.DadosVendaAtualizada;
import br.com.controledevendas.estoque.domain.model.Produto;
import br.com.controledevendas.estoque.domain.model.Venda;
import br.com.controledevendas.estoque.domain.repository.ProdutoRepository;
import br.com.controledevendas.estoque.domain.repository.VendaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class VendaControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void NaoDeveriacadastrarUmaVendaComEstoqueInsuficiente() throws Exception {

        String json = """   
                {	
                 	"idproduto": "1",
                	"quantidade": "999",
                	"dataVenda": "2025-07-24"
                }  
                """;

        var response = mockMvc.perform(post("/vendas")
                .content(json).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest()).andReturn();

    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void DeveriacadastrarUmaVendaComEstoque() throws Exception {
        var produto = new Produto(new DadosCadastroProduto("monitor", new BigDecimal(500.00), 10));
        produtoRepository.saveAndFlush(produto); // importante para garantir que o ID seja gerado

        var dadosVenda = new DadosCadastroVenda(produto.getId(), 10, LocalDate.of(2025, 7, 17)); // cria uma venda do produto com id real
        var json = objectMapper.writeValueAsString(dadosVenda); // transforma as informações em json

        var response = mockMvc.perform(post("/vendas")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertEquals(201, response.getStatus()); // retorna um created
    }


    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void DeveriaBuscarVendas() throws Exception {

        var response = mockMvc.perform(get("/vendas").contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        assertEquals(200, response.getStatus());

    }
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void DeveriaBuscarVendasPorId() throws Exception {

      Long id = 1L;

      var response = mockMvc.perform(get("/vendas/{id}",id).contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();
      assertEquals(200, response.getStatus());

    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void NaoDeveriaDeletarVendasPorIdSeNaoForEncontrado() throws Exception {

        Long id = 1L;

        var response = mockMvc.perform(delete("/vendas/{id}",id).contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        assertEquals(404, response.getStatus());

    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void DeveriaAtualizarVendas() throws Exception {
           // arrange
        var produto = new Produto(new DadosCadastroProduto("monitor", new BigDecimal(500.00), 10));
        produtoRepository.saveAndFlush(produto);

        var venda = new Venda(produto, 2, LocalDate.of(2025,07,17));
        vendaRepository.saveAndFlush(venda);

        var dadosAtualizados = new DadosVendaAtualizada(
                venda.getId(),
                produto.getId(),
                10,
                LocalDate.of(2025, 7, 17)
        );

        var json = objectMapper.writeValueAsString(dadosAtualizados);

        // act + assert
        var response = mockMvc.perform(put("/vendas")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertEquals(200, response.getStatus());

    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void DeveriaBuscarRelatorioVendas() throws Exception {

        var response = mockMvc.perform(get("/vendas/relatorio").contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        assertEquals(200, response.getStatus());

    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void BuscarVendasPorData() throws Exception {

        // Arrange: cria e salva produto
        var produto = new Produto(new DadosCadastroProduto("monitor", new BigDecimal("500.00"), 10));
        produtoRepository.saveAndFlush(produto);

        // Cria e salva venda usando o construtor que associa corretamente o produto
        var venda = new Venda(produto, 5, LocalDate.of(2025, 7, 17));
        vendaRepository.saveAndFlush(venda);

        // Act: chama o endpoint de busca por ano/mês
        var response = mockMvc.perform(get("/vendas/{ano}/{mes}", 2025, 7)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        // Assert: valida status e se a data da venda está no JSON de resposta
        assertEquals(200, response.getStatus());
        var json = response.getContentAsString();
        assertTrue(json.contains("2025-07-17"));

    }


    }




