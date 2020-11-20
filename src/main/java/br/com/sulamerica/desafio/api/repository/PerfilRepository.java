package br.com.sulamerica.desafio.api.repository;

import br.com.sulamerica.desafio.api.model.entity.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {

    public Optional<Perfil> findByNome(String nome);
}
