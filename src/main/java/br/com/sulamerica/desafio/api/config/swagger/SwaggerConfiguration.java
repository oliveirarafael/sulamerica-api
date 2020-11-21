package br.com.sulamerica.desafio.api.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public Docket sulamericaAPI() {
        return new Docket(DocumentationType.SWAGGER_2).
                   select().
                   apis(RequestHandlerSelectors.basePackage("br.com.sulamerica.desafio.api")).
                   paths(PathSelectors.any()).build().
                   apiInfo(apiInfo()).
                   groupName("Desafio SulAmerica").
                   pathMapping("/");
    }

    public ApiInfo apiInfo(){
        return new ApiInfoBuilder().
                   version("1.0.0").
                   title("API Desafio SulAmerica").
                   contact(new Contact("Rafael Rodrigues de Oliveira", "", "")).
                   description("API com os serviços do desafio da SulAmerica").
                   build();
    }

}
