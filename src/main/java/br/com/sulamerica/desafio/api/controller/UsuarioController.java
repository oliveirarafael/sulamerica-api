package br.com.sulamerica.desafio.api.controller;

import br.com.sulamerica.desafio.api.model.entity.Usuario;
import br.com.sulamerica.desafio.api.service.UsuarioService;
import br.com.sulamerica.desafio.api.view.Views;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.List;

import static br.com.sulamerica.desafio.api.controller.endpoint.Endpoints.Usuarios.*;

@RestController
@RequestMapping(USUARIOS_ENDPOINT)
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @GetMapping
    @JsonView(Views.UsuarioView.Dto.class)
    public List<Usuario> getUsuarios(){
        return service.getUsuarios();
    }

    @GetMapping(USUARIOS_POR_ENDPOINT)
    @JsonView(Views.UsuarioView.Dto.class)
    public ResponseEntity<List<Usuario>> getUsuariosPor(@RequestParam(value = "nome", required = false) String nome,
                                                        @RequestParam(value = "cpf", required = false) String cpf,
                                                        @RequestParam(value = "status", required = false) boolean status) {

         return ResponseEntity.ok(service.getUsuarios(nome, cpf, status));
    }

    @GetMapping(USUARIOS_CPF_INICIA_ZERO_ENDPOINT)
    @JsonView(Views.UsuarioView.Dto.class)
    public ResponseEntity<List<Usuario>> getUsuariosCpfComecaComZero(){
        return ResponseEntity.ok(service.getUsuarioCpfComecaComZero());
    }

    @GetMapping(USUARIOS_ID_ENDPOINT)
    @JsonView(Views.UsuarioView.Dto.class)
    public Usuario getUsuarioPorId(@PathVariable Long id){
        return service.getUsuarioPorId(id);
    }

    @PostMapping
    @Transactional
    @JsonView(Views.UsuarioView.Dto.class)
    public ResponseEntity<Usuario> salvar(@RequestBody @Validated(Views.UsuarioView.Form.class) @JsonView(Views.UsuarioView.Form.Post.class) Usuario usuario, UriComponentsBuilder uriBuilder) {
        Usuario usuarioCadastrado = service.salvar(usuario);
        URI uri = uriBuilder.path(USUARIOS_ENDPOINT + USUARIOS_ID_ENDPOINT).buildAndExpand(usuarioCadastrado.getId()).toUri();
        return ResponseEntity.created(uri).body(usuarioCadastrado);
    }

    @PutMapping(USUARIOS_ID_ENDPOINT)
    @Transactional
    @JsonView(Views.UsuarioView.Dto.class)
    public ResponseEntity<Usuario> atualizar(@PathVariable Long id,
                                       @RequestBody @Validated(Views.UsuarioView.Form.class)
                                       @JsonView(Views.UsuarioView.Form.Put.class) Usuario usuario) {

        return ResponseEntity.ok(service.atualizar(id, usuario));
    }

    @DeleteMapping(USUARIOS_ID_ENDPOINT)
    @Transactional
    public ResponseEntity deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }


}
