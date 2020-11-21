package br.com.sulamerica.desafio.api.model;


import br.com.sulamerica.desafio.api.model.entity.Usuario;
import br.com.sulamerica.desafio.api.view.Views;
import com.fasterxml.jackson.annotation.JsonView;

public class Token {

    @JsonView(Views.Autenticacao.Dto.class)
    private String token;
    @JsonView(Views.Autenticacao.Dto.class)
    private String tipo = "Bearer";

	@JsonView(Views.Autenticacao.Dto.class)
	private Usuario usuario;
    
    public Token(String token, Usuario usuario) {
		this.token = token;
		this.usuario = usuario;
    }

	public String getToken() {
		return token;
	}
	public String getTipo() {
		return tipo;
	}

	public Usuario getUsuario() {
		return usuario;
	}
    
}
