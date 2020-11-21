package br.com.sulamerica.desafio.api.controller;

import br.com.sulamerica.desafio.api.model.entity.Perfil;

import br.com.sulamerica.desafio.api.service.PerfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

import static br.com.sulamerica.desafio.api.controller.endpoint.Endpoints.Perfil.*;

@RestController
@RequestMapping(PERFIS_ENDPOINT)
public class PerfilController {

    @Autowired
    private PerfilService service;

    @GetMapping
    public Page<Perfil> getPerfis(@PageableDefault(sort = "nome",
                                            direction = Direction.DESC,
                                            page = 0, size = 10) Pageable paginacao){

        return service.getPerfis(paginacao);
    }

    @GetMapping(PERFIS_ID_ENDPOINT)
    public Perfil getPerfilPorId(@PathVariable Long id){
        return service.getPerfilPorId(id);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Perfil> salvar(@RequestBody @Valid Perfil perfil, UriComponentsBuilder uriBuilder) {
        Perfil perfilCadastrado = service.salvar(perfil);
        URI uri = uriBuilder.path(PERFIS_ENDPOINT + PERFIS_ID_ENDPOINT).buildAndExpand(perfilCadastrado.getId()).toUri();
        return ResponseEntity.created(uri).body(perfilCadastrado);
    }

    @PutMapping(PERFIS_ID_ENDPOINT)
    @Transactional
    public ResponseEntity<Perfil> atualizar(@PathVariable Long id,
                                       @RequestBody @Valid Perfil perfil) {

        return ResponseEntity.ok(service.atualizar(id, perfil));
    }

    @DeleteMapping(PERFIS_ID_ENDPOINT)
    @Transactional
    public ResponseEntity deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
