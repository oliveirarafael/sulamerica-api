package br.com.sulamerica.desafio.api.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.sulamerica.desafio.api.view.Views;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.security.core.GrantedAuthority;

import java.util.Objects;

@Entity
public class Perfil implements GrantedAuthority{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView({Views.Dto.class, Views.UsuarioView.Form.Post.class, Views.UsuarioView.Form.Put.class, Views.UsuarioView.Dto.class})
    private Long id;

    @NotNull(message = "{perfil.nome.not.null}")
    @NotEmpty(message = "perfil.nome.not.blank")
    @JsonView({Views.UsuarioView.Dto.class, Views.Form.Post.class, Views.Form.Put.class, Views.Dto.class})
    private String nome;

    public Perfil() {}

    public Perfil(String nome) {
        this.nome = nome;
    }

    @Override
    public String getAuthority() {
        return nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Perfil perfil = (Perfil) o;
        return Objects.equals(id, perfil.id) &&
                Objects.equals(nome, perfil.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome);
    }

    @Override
    public String toString() {
        return "Perfil [id=" + id + ", nome=" + nome + "]";
    }

}
