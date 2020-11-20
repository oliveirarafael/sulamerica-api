package br.com.sulamerica.desafio.api.repository;

import br.com.sulamerica.desafio.api.config.security.Profiles;
import br.com.sulamerica.desafio.api.model.entity.Cargo;
import br.com.sulamerica.desafio.api.model.entity.Perfil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import javax.validation.ConstraintViolationException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles(Profiles.TESTE)
public class PerfilRepositoryTest {

    public static final String ADMINISTRADOR = "Administrador";
    public static final String COMUM = "Comum";
    @Autowired
    private PerfilRepository repository;

    @Test
    public void salvar(){
        Perfil perfil = repository.save(new Perfil(ADMINISTRADOR));

        assertNotNull(perfil.getId());
    }

    @Test
    public void deletar(){
        Perfil perfil = repository.save(new Perfil(ADMINISTRADOR));
        assertNotNull(perfil);

        repository.delete(perfil);
        assertTrue(repository.findById(perfil.getId()).isEmpty());
    }

    @Test
    public void update(){
        Perfil perfilSalvo = repository.save(new Perfil(ADMINISTRADOR));
        Perfil perfil = repository.findById(perfilSalvo.getId()).get();
        perfil.setNome(COMUM);

        assertEquals(COMUM, repository.findById(perfilSalvo.getId()).get().getNome());
    }

    @Test
    public void deveSelecionarPerfilPeloNome(){
        repository.save(new Perfil(ADMINISTRADOR));
        Perfil perfil = repository.findByNome(ADMINISTRADOR).get();

        assertNotNull(perfil.getId());
    }

}
