package br.com.sulamerica.desafio.api.repository;

import br.com.sulamerica.desafio.api.model.entity.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CargoRepository extends JpaRepository<Cargo, Long> {

    public Optional<Cargo> findByNome(String nome);
}
