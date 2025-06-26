package br.com.controledevendas.estoque.infra.springdoc;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("Controle De Estoque Api").version("1.0")
                .description("API de Controle de Vendas com Estoque")
                .contact(new Contact().name("Wesley").email("wesleydsilva96@gmail.com")));
    }
}
