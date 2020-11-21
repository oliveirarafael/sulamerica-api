package br.com.sulamerica.desafio.api.controller;

import br.com.sulamerica.desafio.api.builder.UsuarioDataBuilderTest;
import br.com.sulamerica.desafio.api.config.security.Profiles;
import br.com.sulamerica.desafio.api.controller.endpoint.Endpoints;
import br.com.sulamerica.desafio.api.model.entity.Cargo;
import br.com.sulamerica.desafio.api.model.entity.Perfil;
import br.com.sulamerica.desafio.api.model.entity.Usuario;
import br.com.sulamerica.desafio.api.repository.CargoRepository;
import br.com.sulamerica.desafio.api.repository.PerfilRepository;
import br.com.sulamerica.desafio.api.repository.UsuarioRepository;
import br.com.sulamerica.desafio.api.view.Views;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(Profiles.TESTE)
public class AutenticacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private UsuarioRepository usuarioRepository;

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
    public void deveRetornarForbiddenMetodoGet() throws Exception {
        mockMvc.perform(get(Endpoints.Autenticacao.AUTH_ENDPOINT)).andDo(print()).andExpect(status().isForbidden());
    }

    @Test
    public void deveRetornarBadRequestCasoUsuarioSejaInvalido() throws Exception {
        String nome = "usuario.teste.2";
        String senha = "123456";

        String json = mapper.writerWithView(Views.AutenticacaoView.Form.class).writeValueAsString(new Usuario(nome, senha));

        var request = post(Endpoints.Autenticacao.AUTH_ENDPOINT).content(json).contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andDo(print())
                                .andExpect(jsonPath("$.mensagem").value("Usuário Inválido"))
                                .andExpect(status().isBadRequest());

    }

    @Test
    public void deveRetornarTokenGerado() throws Exception {
        String nome = "usuario.teste.1";
        String senha = "123456";

        Usuario usuario = UsuarioDataBuilderTest.builder()
                .comNome(nome)
                .comCpf("878.892.940-00")
                .comDataNascimento(LocalDate.of(1985, 5, 12))
                .masculino()
                .comSenha(new BCryptPasswordEncoder().encode(senha))
                .comCargo(cargo)
                .comPerfil(perfil)
                .ativo()
                .build();
        
        usuarioRepository.save(usuario);

        String json = mapper.writerWithView(Views.AutenticacaoView.Form.class).writeValueAsString(new Usuario(nome, senha));
        var request = post(Endpoints.Autenticacao.AUTH_ENDPOINT).content(json).contentType(MediaType.APPLICATION_JSON);
      
        mockMvc.perform(request).andExpect(status().isOk())
                                .andExpect(jsonPath("$.token").isNotEmpty())
                                .andExpect(jsonPath("$.tipo").value("Bearer"))
                                .andDo(print());

    }

    @Test
    public void deveRetornarBadRequestCasoUsuarioBloquado() throws Exception {
        String nome = "usuario.teste.bloqueado";
        String senha = "123456";

        Usuario usuario = UsuarioDataBuilderTest.builder()
                                                .comNome(nome)
                                                .comCpf("878.892.940-00")
                                                .comDataNascimento(LocalDate.of(1985, 5, 12))
                                                .masculino()
                                                .comSenha(new BCryptPasswordEncoder().encode(senha))
                                                .comCargo(cargo)
                                                .comPerfil(perfil)
                                                .inativo()
                                                .build();

        usuarioRepository.save(usuario);

        String json = mapper.writerWithView(Views.AutenticacaoView.Form.class).writeValueAsString(new Usuario(nome, senha));
        var request = post(Endpoints.Autenticacao.AUTH_ENDPOINT).content(json).contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andDo(print())
                .andExpect(jsonPath("$.mensagem").value("Usuário Bloqueado"))
                .andExpect(status().isBadRequest());

    }
}
