package br.com.sulamerica.desafio.api.service;

import br.com.sulamerica.desafio.api.builder.UsuarioDataBuilderTest;
import br.com.sulamerica.desafio.api.config.security.Profiles;
import br.com.sulamerica.desafio.api.exception.ConflictException;
import br.com.sulamerica.desafio.api.exception.NotFoundException;
import br.com.sulamerica.desafio.api.model.entity.Cargo;
import br.com.sulamerica.desafio.api.model.entity.Perfil;
import br.com.sulamerica.desafio.api.model.entity.Usuario;
import br.com.sulamerica.desafio.api.repository.CargoRepository;
import br.com.sulamerica.desafio.api.repository.PerfilRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles(Profiles.TESTE)
public class UsuarioServiceTest {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private CargoRepository cargoRepository;

    @Autowired
    private PerfilRepository perfilRepository;

    private Cargo cargo;
    private Perfil perfil;

    @BeforeEach
    public void setup(){
        cargo = cargoRepository.save(new Cargo("Gerente"));
        perfil = perfilRepository.save(new Perfil("Administrador"));
    }

    @Test
    public void salvar(){
        Usuario usuario = UsuarioDataBuilderTest.builder()
                                                .comNome("Fulano.1")
                                                .comCpf("12346584690")
                                                .comDataNascimento(LocalDate.of(1985, 5, 12))
                                                .masculino()
                                                .comSenha("123456")
                                                .comCargo(cargo)
                                                .comPerfil(perfil)
                                                .build();

        Usuario usuarioSalvo = this.usuarioService.salvar(usuario);
        assertNotNull(usuarioSalvo.getId());
    }

    @Test
    public void deveLancarExceptionSeUsuarioJaCadastrado(){
        Usuario usuario = UsuarioDataBuilderTest.builder()
                                                .comNome("Fulano.2")
                                                .comCpf("12346584690")
                                                .comDataNascimento(LocalDate.of(1985, 5, 12))
                                                .masculino()
                                                .comSenha("123456")
                                                .comCargo(cargo)
                                                .comPerfil(perfil)
                                                .build();

        usuarioService.salvar(usuario);

       assertThrows(ConflictException.class, () -> {
            usuarioService.salvar(usuario);
        });
    }

    @Test
    public void deletar(){
        Usuario usuario = UsuarioDataBuilderTest.builder()
                .comNome("Fulano.3")
                .comCpf("12346584690")
                .comDataNascimento(LocalDate.of(1985, 5, 12))
                .masculino()
                .comSenha("123456")
                .comCargo(cargo)
                .comPerfil(perfil)
                .build();

        Usuario usuarioSalvo = usuarioService.salvar(usuario);
        assertNotNull(usuarioSalvo.getId());

        usuarioService.deletar(usuarioSalvo.getId());
        assertThrows(NotFoundException.class, () -> {
           usuarioService.getUsuarioPorId(usuarioSalvo.getId());
        });
    }

    @Test
    public void atualizar(){
        Usuario usuario = UsuarioDataBuilderTest.builder()
                .comNome("Fulano.4")
                .comCpf("12346584690")
                .comDataNascimento(LocalDate.of(1985, 5, 12))
                .masculino()
                .comSenha("123456")
                .comCargo(cargo)
                .comPerfil(perfil)
                .build();

        Usuario usuarioSalvo = usuarioService.salvar(usuario);
        assertNotNull(usuarioSalvo.getId());

        Usuario usuarioAtualizado = UsuarioDataBuilderTest.builder()
                .comNome("Fulano.5")
                .comCpf("11111111111")
                .comDataNascimento(LocalDate.of(1985, 5, 12))
                .masculino()
                .comSenha("123456")
                .comCargo(cargo)
                .comPerfil(perfil)
                .build();

        usuarioService.atualizar(usuarioSalvo.getId(), usuarioAtualizado);

        Usuario usuarioConsultado = usuarioService.getUsuarioPorId(usuarioSalvo.getId());

        assertEquals(usuarioConsultado.getUsername(), usuarioAtualizado.getUsername());
        assertEquals(usuarioConsultado.getCpf(), usuarioAtualizado.getCpf());
    }

    @Test
    public void naoDeveAtualizarUsuarioCasoJaExistaUsuarioComNomeECpfCadastrado(){
        Usuario usuario = UsuarioDataBuilderTest.builder()
                .comNome("Fulano.4")
                .comCpf("12346584690")
                .comDataNascimento(LocalDate.of(1985, 5, 12))
                .masculino()
                .comSenha("123456")
                .comCargo(cargo)
                .comPerfil(perfil)
                .build();

        Usuario usuarioSalvo = usuarioService.salvar(usuario);

        assertThrows(ConflictException.class, () -> {
            usuarioService.atualizar(usuarioSalvo.getId(), usuario);
        });
    }

}
