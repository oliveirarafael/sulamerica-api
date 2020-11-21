package br.com.sulamerica.desafio.api.controller;

import br.com.sulamerica.desafio.api.model.entity.Usuario;
import br.com.sulamerica.desafio.api.service.UsuarioService;
import br.com.sulamerica.desafio.api.view.Views;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import java.net.URI;

import static br.com.sulamerica.desafio.api.controller.endpoint.Endpoints.Usuarios.USUARIOS_ENDPOINT;
import static br.com.sulamerica.desafio.api.controller.endpoint.Endpoints.Usuarios.USUARIOS_ID_ENDPOINT;

@RestController
@RequestMapping(USUARIOS_ENDPOINT)
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @GetMapping
    @JsonView(Views.UsuarioView.Dto.class)
    public Page<Usuario> getUsuarios(@PageableDefault(sort = "nome",
                                                      direction = Direction.DESC,
                                                      page = 0, size = 10) Pageable paginacao){

        return service.getUsuarios(paginacao);
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
