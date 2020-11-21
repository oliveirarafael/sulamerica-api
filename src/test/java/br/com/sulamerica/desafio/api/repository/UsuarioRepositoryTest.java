package br.com.sulamerica.desafio.api.repository;

import br.com.sulamerica.desafio.api.builder.UsuarioDataBuilderTest;
import br.com.sulamerica.desafio.api.config.security.Profiles;
import br.com.sulamerica.desafio.api.model.entity.Cargo;
import br.com.sulamerica.desafio.api.model.entity.Perfil;
import br.com.sulamerica.desafio.api.model.entity.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@ActiveProfiles(Profiles.TESTE)
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private CargoRepository cargoRepository;
    @Autowired
    private PerfilRepository perfilRepository;

    private Usuario usuario;

    @BeforeEach
    public void setup(){
        Cargo cargo = cargoRepository.save(new Cargo("Gerente"));
        Perfil perfil = perfilRepository.save(new Perfil("Administrador"));

        this.usuario = UsuarioDataBuilderTest.builder()
                                        .comNome("Fulano")
                                        .comCpf("02346584690")
                                        .comDataNascimento(LocalDate.of(1985, 5, 12))
                                        .masculino()
                                        .comSenha("123456")
                                        .comCargo(cargo)
                                        .comPerfil(perfil)
                                        .build();

    }

    @Test
    public void salvar(){
        Usuario usuario = usuarioRepository.save(this.usuario);

        assertNotNull(usuario.getId());
    }

    @Test
    public void deletar(){
        Usuario usuario = usuarioRepository.save(this.usuario);
        assertNotNull(usuario);

        usuarioRepository.delete(usuario);
        assertTrue(usuarioRepository.findById(usuario.getId()).isEmpty());
    }

    @Test
    public void update(){
        Usuario usuarioSalvo = usuarioRepository.save(this.usuario);
        Usuario usuario = usuarioRepository.findById(usuarioSalvo.getId()).get();
        usuario.setCpf("11111111111");

        assertEquals("11111111111", usuarioRepository.findById(usuarioSalvo.getId()).get().getCpf());
    }

    @Test
    public void deveSelecionarUsuarioPeloNome(){
        usuarioRepository.save(this.usuario);
        Usuario usuario = usuarioRepository.findByNome("Fulano").get();

        assertNotNull(usuario);
    }

    @Test
    public void deveSelecionarUsuarioPeloNomeECpf(){
        usuarioRepository.save(this.usuario);
        Usuario usuario = usuarioRepository.findByNomeAndCpf("Fulano", "02346584690").get();
        assertNotNull(usuario);
    }

    @Test
    public void deveSelecionarUsuarioComCpfIniciaComZero(){
        usuarioRepository.save(this.usuario);

        List<Usuario> usuario = usuarioRepository.findCpfsComecaZero();
        assertEquals(1, usuario.size());
    }


}
