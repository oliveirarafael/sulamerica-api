package br.com.sulamerica.desafio.api.repository;

import br.com.sulamerica.desafio.api.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>, JpaSpecificationExecutor<Usuario> {
    @Query("SELECT u FROM Usuario u join fetch u.cargo join fetch u.perfis")
    List<Usuario> findAll();

    Optional<Usuario> findByNome(String nome);
    Optional<Usuario> findByNomeAndCpf(String nome, String cpf);

    @Query("SELECT u FROM Usuario u where u.cpf LIKE '0%'")
    List<Usuario> findCpfsComecaZero();
}
