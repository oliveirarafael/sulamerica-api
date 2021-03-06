package br.com.sulamerica.desafio.api.model.entity;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.sulamerica.desafio.api.model.entity.enums.Sexo;
import br.com.sulamerica.desafio.api.validation.DataNascimentoValid;
import br.com.sulamerica.desafio.api.view.Views;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class Usuario implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonView(value = {Views.UsuarioView.Dto.class, Views.AutenticacaoView.Dto.class})
	private Long id;

	@NotNull(groups = {Views.UsuarioView.Form.class, Views.AutenticacaoView.Form.class})
	@NotEmpty(groups = {Views.UsuarioView.Form.class, Views.AutenticacaoView.Form.class})
	@JsonView(value = {Views.UsuarioView.Dto.class, Views.UsuarioView.Form.Post.class,
			           Views.UsuarioView.Form.Put.class, Views.AutenticacaoView.Dto.class, Views.AutenticacaoView.Form.class})
	private String nome;

	@NotEmpty(groups = {Views.UsuarioView.Form.Post.class, Views.AutenticacaoView.Form.class})
	@NotNull(groups = {Views.UsuarioView.Form.Post.class, Views.AutenticacaoView.Form.class})
	@JsonView(value = {Views.UsuarioView.Form.Post.class,
			Views.UsuarioView.Form.Put.class,
			Views.AutenticacaoView.Form.class})
	private String senha;

	@NotNull(groups = Views.UsuarioView.Form.class)
	@NotEmpty(groups = Views.UsuarioView.Form.class)
	@CPF(groups = Views.UsuarioView.Form.class)
	@JsonView(value = {Views.UsuarioView.Dto.class, Views.UsuarioView.Form.Post.class, Views.UsuarioView.Form.Put.class})
	private String cpf;

	@Enumerated(EnumType.STRING)
	@NotNull(groups = Views.UsuarioView.Form.class)
	@JsonView(value = {Views.UsuarioView.Dto.class, Views.UsuarioView.Form.Post.class, Views.UsuarioView.Form.Put.class})
	private Sexo sexo;

	@NotNull(groups = Views.UsuarioView.Form.class)
	@DataNascimentoValid(groups = Views.UsuarioView.Form.class)
	@JsonView(value = {Views.UsuarioView.Dto.class, Views.UsuarioView.Form.Post.class, Views.UsuarioView.Form.Put.class})
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataNascimento;

	@JsonView(value = {Views.UsuarioView.Dto.class, Views.UsuarioView.Form.Post.class, Views.UsuarioView.Form.Put.class})
	private boolean status = true;


	@ManyToOne
	@NotNull(groups = Views.UsuarioView.Form.class)
	@JsonView(value = {Views.UsuarioView.Dto.class, Views.UsuarioView.Form.Post.class, Views.UsuarioView.Form.Put.class})
	private Cargo cargo;

	@ManyToMany(fetch = FetchType.EAGER)
	@NotNull(groups = Views.UsuarioView.Form.class)
	@JsonView(value = {Views.UsuarioView.Dto.class, Views.UsuarioView.Form.Post.class, Views.UsuarioView.Form.Put.class})
	private List<Perfil> perfis;

	public Usuario() {
	}

	public Usuario(String nome, String cpf, Sexo sexo,
				   LocalDate dataNascimento, boolean status, String senha,
				   Cargo cargo, List<Perfil> perfis) {

		this.nome = nome;
		this.cpf = cpf;
		this.sexo = sexo;
		this.dataNascimento = dataNascimento;
		this.status = status;
		this.senha = senha;
		this.cargo = cargo;
		this.perfis = perfis;
	}

	public Usuario(String nome, String senha) {
		this.nome = nome;
		this.senha = senha;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Sexo getSexo() {
		return sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.perfis;
	}

	public void setAuthorities(List<Perfil> perfis){
		this.perfis = perfis;
	}

	@Override
	public String getPassword() {
		return this.senha;
	}

	public void setPassword(String password) {
		this.senha = password;
	}

	@Override
	public String getUsername() {
		return this.nome;
	}

	public void setUsername(String username){
		this.nome = username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return status;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Usuario usuario = (Usuario) o;
		return status == usuario.status &&
				Objects.equals(id, usuario.id) &&
				Objects.equals(nome, usuario.nome) &&
				Objects.equals(cpf, usuario.cpf) &&
				sexo == usuario.sexo &&
				Objects.equals(dataNascimento, usuario.dataNascimento) &&
				Objects.equals(senha, usuario.senha) &&
				Objects.equals(cargo, usuario.cargo) &&
				Objects.equals(perfis, usuario.perfis);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, nome, cpf, sexo, dataNascimento, status, senha, cargo, perfis);
	}

	@Override
	public String toString() {
		return "Usuario{" +
				"id=" + id +
				", nome='" + nome + '\'' +
				", cpf='" + cpf + '\'' +
				", sexo=" + sexo +
				", dataNascimento=" + dataNascimento +
				", status=" + status +
				", senha='" + senha + '\'' +
				", cargo=" + cargo +
				", perfis=" + perfis +
				'}';
	}
}
