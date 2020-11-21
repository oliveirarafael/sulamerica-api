package br.com.sulamerica.desafio.api.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Erro {
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataHora = LocalDateTime.now();
    private String mensagem;
    private Integer codigo;
    private String status;
    

    public Erro(String mensagem, Integer codigo, String status){
        this.mensagem = mensagem;
        this.codigo = codigo;
        this.status = status;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public String getMensagem() {
        return mensagem;
    }
    
    public String getStatus() {
		return status;
	}
    
    public Integer getCodigo() {
		return codigo;
	}
}