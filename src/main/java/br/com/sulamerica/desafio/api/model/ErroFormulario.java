package br.com.sulamerica.desafio.api.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ErroFormulario {
	
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataHora = LocalDateTime.now();
	private String campo;
	private String erro;
	private Integer codigo;
    private String status;
	
	public ErroFormulario(String campo, String erro, Integer codigo, String status) {
		this.campo = campo;
		this.erro = erro;
		this.codigo = codigo;
		this.status = status;
	}

	public String getCampo() {
		return campo;
	}

	public String getErro() {
		return erro;
	}
	
	public LocalDateTime getDataHora() {
        return dataHora;
    }

    public String getStatus() {
		return status;
	}
    
    public Integer getCodigo() {
		return codigo;
	}
	

}