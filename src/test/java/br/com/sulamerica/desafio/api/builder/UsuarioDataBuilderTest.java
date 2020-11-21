package br.com.sulamerica.desafio.api.builder;

import br.com.sulamerica.desafio.api.model.entity.Cargo;
import br.com.sulamerica.desafio.api.model.entity.Perfil;
import br.com.sulamerica.desafio.api.model.entity.Usuario;
import br.com.sulamerica.desafio.api.model.entity.enums.Sexo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDataBuilderTest {
    private String nome;
    private String cpf;
    private Sexo sexo;
    private LocalDate dataNascimento;
    private boolean status;
    private String senha;
    private Cargo cargo;
    private List<Perfil> perfis = new ArrayList<>();

    private UsuarioDataBuilderTest(){}

    public static UsuarioDataBuilderTest builder(){
        return new UsuarioDataBuilderTest();
    }

    public UsuarioDataBuilderTest comNome(String nome){
        this.nome = nome;
        return this;
    }

    public UsuarioDataBuilderTest comCpf(String cpf){
        this.cpf = cpf;
        return this;
    }

    public UsuarioDataBuilderTest masculino(){
        this.sexo = Sexo.MASCULINO;
        return this;
    }

    public UsuarioDataBuilderTest feminino() {
        this.sexo = Sexo.FEMININO;
        return this;
    }

    public UsuarioDataBuilderTest outro(){
        this.sexo = Sexo.OUTROS;
        return this;
    }

    public UsuarioDataBuilderTest comDataNascimento(LocalDate dataNAscimento){
        this.dataNascimento = dataNAscimento;
        return this;
    }

    public UsuarioDataBuilderTest comSenha(String senha){
        this.senha = senha;
        return this;
    }

    public UsuarioDataBuilderTest comCargo(Cargo cargo){
        this.cargo = cargo;
        return this;
    }

    public UsuarioDataBuilderTest comPerfil(Perfil perfil){
        this.perfis.add(perfil);
        return this;
    }

    public UsuarioDataBuilderTest ativo(){
        this.status = true;
        return this;
    }

    public UsuarioDataBuilderTest inativo(){
        this.status = false;
        return this;
    }

    public Usuario build(){
        return new Usuario(nome, cpf, sexo, dataNascimento, status, senha, cargo, perfis);
    }
}
