package br.com.controledevendas.estoque.controller;

import br.com.controledevendas.estoque.dto.produto.DadosCadastroProduto;
import br.com.controledevendas.estoque.dto.vendas.DadosCadastroVenda;
import br.com.controledevendas.estoque.dto.vendas.DadosVendaAtualizada;
import br.com.controledevendas.estoque.entity.Produto;
import br.com.controledevendas.estoque.entity.Venda;
import br.com.controledevendas.estoque.repository.ProdutoRepository;
import br.com.controledevendas.estoque.repository.VendaRepository;
import br.com.controledevendas.estoque.service.ProdutoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class VendaControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProdutoRepository produtoRepository; // usa o real

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

        var dadosVenda = new DadosCadastroVenda(produto.getId(), 10, LocalDate.of(2025, 7, 17));
        var json = objectMapper.writeValueAsString(dadosVenda);

        var response = mockMvc.perform(post("/vendas")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertEquals(201, response.getStatus());
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

//    @Test
//    @WithMockUser(username = "admin", roles = {"ADMIN"})
//    void BuscarVendasPorData() throws Exception {
//
//        var produto = new Produto(new DadosCadastroProduto("monitor", new BigDecimal(500.00), 10));
//        produtoRepository.saveAndFlush(produto);
//
//        var venda = new Venda(new DadosCadastroVenda(produto.getId(), 5, LocalDate.of(2025, 7, 17),produto));
//        vendaRepository.saveAndFlush(venda);
//
//        var response = mockMvc.perform(get("/vendas/{ano}/{mes}", 2025, 7)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andReturn()
//                .getResponse();
//        assertEquals(200, response.getStatus());
//        var json = response.getContentAsString();
//
//       assertTrue(json.contains("2025-07-17"));
//
//    }


    }




