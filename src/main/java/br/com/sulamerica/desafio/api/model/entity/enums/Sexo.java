package br.com.sulamerica.desafio.api.model.entity.enums;

public enum Sexo {
    MASCULINO("M"), FEMININO("F"), OUTROS("O");

    private String valor;

    public String getValor() {
        return valor;
    }

    Sexo(String valor){
        this.valor = valor;
    }
}
