package br.com.sulamerica.desafio.api.repository;

import br.com.sulamerica.desafio.api.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByNome(String nome);
    Optional<Usuario> findByNomeAndCpf(String nome, String cpf);
}
