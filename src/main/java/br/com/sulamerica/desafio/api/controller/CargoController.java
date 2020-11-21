package br.com.sulamerica.desafio.api.controller;

import java.net.URI;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import br.com.sulamerica.desafio.api.model.entity.Cargo;
import br.com.sulamerica.desafio.api.service.CargoService;
import br.com.sulamerica.desafio.api.view.Views;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import static br.com.sulamerica.desafio.api.controller.endpoint.Endpoints.Cargos.*;

@RestController
@RequestMapping(CARGOS_ENDPOINT)
public class CargoController {

    @Autowired
    private CargoService service;

    @GetMapping
    @JsonView(Views.Dto.class)
    public List<Cargo> getCargos(){
        return service.getCargos();
    }

    @GetMapping(CARGOS_ID_ENDPOINT)
    @JsonView(Views.Dto.class)
    public Cargo getCargosPorId(@PathVariable Long id){
        return service.getCargoPorId(id);
    }

    @PostMapping
    @Transactional
    @JsonView(Views.Dto.class)
    public ResponseEntity<Cargo> salvar(@RequestBody @Valid @JsonView(Views.Form.Post.class) Cargo cargo, UriComponentsBuilder uriBuilder) {
        Cargo cargoCadastrado = service.salvar(cargo);
        URI uri = uriBuilder.path(CARGOS_ENDPOINT + CARGOS_ID_ENDPOINT).buildAndExpand(cargoCadastrado.getId()).toUri();
        return ResponseEntity.created(uri).body(cargoCadastrado);
    }

    @PutMapping(CARGOS_ID_ENDPOINT)
    @Transactional
    @JsonView(Views.Dto.class)
    public ResponseEntity<Cargo> atualizar(@PathVariable Long id,
                                           @RequestBody @Valid @JsonView(Views.Form.Put.class) Cargo cargo) {

        return ResponseEntity.ok(service.atualizar(id, cargo));
    }

    @DeleteMapping(CARGOS_ID_ENDPOINT)
    @Transactional
    public ResponseEntity deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
