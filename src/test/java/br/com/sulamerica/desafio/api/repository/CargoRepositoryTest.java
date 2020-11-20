package br.com.sulamerica.desafio.api.repository;

import br.com.sulamerica.desafio.api.config.security.Profiles;
import br.com.sulamerica.desafio.api.model.entity.Cargo;
import br.com.sulamerica.desafio.api.model.entity.Usuario;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@ActiveProfiles(Profiles.TESTE)
public class CargoRepositoryTest {

    public static final String GERENTE = "Gerente";
    public static final String SUPERVISOR = "Supervisor";
    @Autowired
    private CargoRepository repository;

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    public void salvar(){
        Cargo cargo = repository.save(new Cargo("Gerente"));

        assertNotNull(cargo.getId());
    }

    @Test
    public void deletar(){
        Cargo cargo = repository.save(new Cargo(GERENTE));
        assertNotNull(cargo);

        repository.delete(cargo);
        assertTrue(repository.findById(cargo.getId()).isEmpty());
    }

    @Test
    public void update(){
        Cargo cargoSalvo = repository.save(new Cargo(GERENTE));
        Cargo cargo = repository.findById(cargoSalvo.getId()).get();
        cargo.setNome(SUPERVISOR);

        assertEquals(SUPERVISOR, repository.findById(cargoSalvo.getId()).get().getNome());
    }

    @Test
    public void deveSelecionarCargoPeloNome(){
        repository.save(new Cargo(GERENTE));
        Cargo cargo = repository.findByNome(GERENTE).get();

        assertNotNull(cargo.getId());
    }
}
