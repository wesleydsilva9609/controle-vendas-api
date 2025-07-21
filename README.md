# controle-vendas-api
# 🚀 Controle de Vendas e Estoque API

API REST desenvolvida em **Spring Boot** para gerenciar produtos, vendas e relatórios de estoque, com autenticação via **JWT** e documentação **Swagger**.

> **Status:** Em desenvolvimento – Novas funcionalidades e testes unitários em breve!

---

## ✨ Funcionalidades

- **CRUD de Produtos**
- **Registro de Vendas** com atualização automática de estoque
- **Busca de vendas por data (Ano/Mês)**
- **Relatório detalhado de vendas**
- **Autenticação JWT** com níveis de acesso:
  - **Admin:** cadastrar, atualizar e deletar produtos
  - **Usuário autenticado:** consultar vendas e relatórios
- **Documentação interativa com Swagger** em `/swagger-ui.html`

---

## 🛠 Tecnologias

- **Java 17**
- **Spring Boot 3**
- **Spring Data JPA**
- **Spring Security (JWT)**
- **PostgreSQL**
- **JUnit 5 + MockMvc**
- **Swagger/OpenAPI**
- **Maven**

---
