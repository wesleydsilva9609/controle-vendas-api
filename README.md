# 📦 Controle de Vendas API

API RESTful para **gestão de vendas e estoque**, desenvolvida em **Java 17** com **Spring Boot**.  
Permite o cadastro, atualização, listagem e exclusão de produtos e vendas, além de autenticação com JWT.

---

## 🚀 Funcionalidades
- **Cadastro e gerenciamento de produtos**
- **Registro de vendas** e controle automático de estoque
- **Autenticação com JWT** (login e cadastro de usuários)
- **Relatórios mensais de vendas**
- **Documentação interativa com Swagger**

---

## 🛠️ Tecnologias Utilizadas
- **Java 17**
- **Spring Boot 3**
- **Spring Data JPA**
- **PostgreSQL**
- **JWT para autenticação**
- **Swagger/OpenAPI**
- **JUnit e Mockito** (testes unitários)

---

## 📸 Documentação da API

### Swagger UI
<img width="1920" height="1080" alt="imagem_2025-07-24_160601921" src="https://github.com/user-attachments/assets/2c2f8b47-e6a9-4fad-b467-66f7555454b2" />
<img width="1920" height="1080" alt="imagem_2025-07-24_161231011" src="https://github.com/user-attachments/assets/6cb90ecd-c166-430d-b433-a6812e27a408" />



> **Dica**: Para acessar, rode o projeto e vá até `http://localhost:8080/swagger-ui/index.html`

---

## 📂 Como Rodar o Projeto

### Pré-requisitos
- Java 17+
- Maven 3.8+
- PostgreSQL

### Passos
```bash
# Clonar repositório
git clone https://github.com/seu-usuario/controle-vendas-api.git

# Entrar na pasta
cd controle-vendas-api

# Configurar variáveis de ambiente (.env ou application.properties)
DB_URL=jdbc:postgresql://localhost:5432/controle_vendas
DB_USER=seu_usuario
DB_PASSWORD=sua_senha

# Rodar o projeto
./mvnw spring-boot:run

---
```

## 🧪 Testes
Rodar os testes unitários:
```
bash
Copiar
Editar
./mvnw test
```

## 🔑 Autenticação
A API utiliza JWT. Primeiro faça login:
```
POST /login
{
  "usuario": "admin",
  "senha": "123456"
}
```
Depois use o token nos endpoints protegidos.

## 👨‍💻 Autor
Wesley Silva

<h2>  📫 Contato </h2>
<p 
  <a href="mailto:wesleydsilva96@gmail.com" target="_blank">
    <img src="https://img.shields.io/badge/Gmail-D14836?style=for-the-badge&logo=gmail&logoColor=white" />
  </a>
  <a href="https://www.linkedin.com/in/wesley-silva-01a134346/" target="_blank">
    <img src="https://img.shields.io/badge/-LinkedIn-%230077B5?style=for-the-badge&logo=linkedin&logoColor=white" />
  </a>
</p>
