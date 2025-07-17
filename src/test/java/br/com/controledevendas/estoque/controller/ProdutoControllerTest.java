package br.com.controledevendas.estoque.controller;

import br.com.controledevendas.estoque.dto.produto.DadosAtualizarProduto;
import br.com.controledevendas.estoque.dto.produto.DadosCadastroProduto;
import br.com.controledevendas.estoque.entity.Produto;
import br.com.controledevendas.estoque.repository.ProdutoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProdutoControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ProdutoRepository produtoRepository;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void DeveriacadastrarProduto() throws Exception {
        DadosCadastroProduto json = new DadosCadastroProduto("monitor",new BigDecimal(50.00),10);
      String request =  objectMapper.writeValueAsString(json);
     var response =   mockMvc.perform(post("/produtos").contentType(MediaType.APPLICATION_JSON).content(request)).andReturn().getResponse();

     assertEquals(201, response.getStatus());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void NaoDeveriacadastrarProdutoSeEstiverCampoEmBranco() throws Exception {
        String json = """
                {
                "nome": "",
                "preco": 100.0,
                "quantidade": 5
                } 
                """;

        mockMvc.perform(post("/produtos")
                .content(json).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());

    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void DeveriaListarProdutos() throws Exception {
         mockMvc.perform(MockMvcRequestBuilders.get("/produtos")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn().getResponse();
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void DeveriaBuscarProdutoPorId() throws Exception {
        var id = 2;

         mockMvc.perform(MockMvcRequestBuilders.get("/produtos/2")
                .param("id", String.valueOf(id))).andExpect(status().isOk()).andReturn().getResponse();
    }
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void DeveriaNaoEncontrarProdutoSeIdNaoExistir() throws Exception {

        Long idInexistente = 999L;

        mockMvc.perform(MockMvcRequestBuilders.get("/produtos/" + idInexistente))
                .andExpect(status().isNotFound());

    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})// arrumar para erro 204
    void DeveriaDeletarProduto() throws Exception {
        var produto = new Produto(new DadosCadastroProduto("monitor",new BigDecimal(500.00),10));
        produtoRepository.save(produto);

     var response =  mockMvc.perform(delete("/produtos/{id}",produto.getId())).andReturn().getResponse();

     assertEquals(204, response.getStatus());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void NaoDeveriaDeletarProdutoInexistente() throws Exception {
        Long idInexistente = 999L;
       // Mockito.when(produtoService.buscarPorID(idInexistente)).thenThrow(EntityNotFoundException.class);
        mockMvc.perform(delete("/produtos/" + idInexistente)).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void NaoDeveriaAtualizarProdutoSeIdNaoForInformado() throws Exception {

        String json = """
                {
                "id": 
                "nome": "teclado"
                } 
                """;

         mockMvc.perform(MockMvcRequestBuilders.put("/produtos").content(json).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());

    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void DeveriaAtualizarProduto() throws Exception {
       // arrange
        var produto = new Produto(new DadosCadastroProduto("monitor gamer",new BigDecimal(500.00),10));
        produtoRepository.save(produto);
        DadosAtualizarProduto atualizarProduto = new DadosAtualizarProduto(produto.getId(),"monitor gamer 240hz",new BigDecimal(500.00),10);
        var json = objectMapper.writeValueAsString(atualizarProduto);


         // act + assert
        mockMvc.perform(MockMvcRequestBuilders.put("/produtos")
                .content(json).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

    }



}