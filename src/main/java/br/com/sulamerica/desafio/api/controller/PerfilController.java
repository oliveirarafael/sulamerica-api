package br.com.sulamerica.desafio.api.controller;

import br.com.sulamerica.desafio.api.model.entity.Perfil;

import br.com.sulamerica.desafio.api.service.PerfilService;
import br.com.sulamerica.desafio.api.view.Views;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static br.com.sulamerica.desafio.api.controller.endpoint.Endpoints.Perfis.*;

@RestController
@RequestMapping(PERFIS_ENDPOINT)
public class PerfilController {

    @Autowired
    private PerfilService service;

    @GetMapping
    @JsonView(Views.Dto.class)
    public List<Perfil> getPerfis(){
        return service.getPerfis();
    }

    @GetMapping(PERFIS_ID_ENDPOINT)
    @JsonView(Views.Dto.class)
    public Perfil getPerfilPorId(@PathVariable Long id){
        return service.getPerfilPorId(id);
    }

    @PostMapping
    @Transactional
    @JsonView(Views.Dto.class)
    public ResponseEntity<Perfil> salvar(@RequestBody @Valid @JsonView(Views.Form.Post.class) Perfil perfil, UriComponentsBuilder uriBuilder) {
        Perfil perfilCadastrado = service.salvar(perfil);
        URI uri = uriBuilder.path(PERFIS_ENDPOINT + PERFIS_ID_ENDPOINT).buildAndExpand(perfilCadastrado.getId()).toUri();
        return ResponseEntity.created(uri).body(perfilCadastrado);
    }

    @PutMapping(PERFIS_ID_ENDPOINT)
    @Transactional
    @JsonView(Views.Dto.class)
    public ResponseEntity<Perfil> atualizar(@PathVariable Long id,
                                       @RequestBody @Valid @JsonView(Views.Form.Put.class) Perfil perfil) {

        return ResponseEntity.ok(service.atualizar(id, perfil));
    }

    @DeleteMapping(PERFIS_ID_ENDPOINT)
    @Transactional
    public ResponseEntity deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
