package br.com.sulamerica.desafio.api.controller;

import java.net.URI;

import javax.transaction.Transactional;
import javax.validation.Valid;

import br.com.sulamerica.desafio.api.model.entity.Cargo;
import br.com.sulamerica.desafio.api.service.CargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
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

import static br.com.sulamerica.desafio.api.controller.endpoint.Endpoints.Cargo.*;

@RestController
@RequestMapping(CARGOS_ENDPOINT)
public class CargoController {

    @Autowired
    private CargoService service;

    @GetMapping
    public Page<Cargo> getCargos(@PageableDefault(sort = "nome",
                                            direction = Direction.DESC,
                                            page = 0, size = 10) Pageable paginacao){

        return service.getCargos(paginacao);
    }

    @GetMapping(CARGOS_ID_ENDPOINT)
    public Cargo getCargosPorId(@PathVariable Long id){
        return service.getCargoPorId(id);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Cargo> salvar(@RequestBody @Valid Cargo cargo, UriComponentsBuilder uriBuilder) {
        Cargo cargoCadastrado = service.salvar(cargo);
        URI uri = uriBuilder.path(CARGOS_ENDPOINT + CARGOS_ID_ENDPOINT).buildAndExpand(cargoCadastrado.getId()).toUri();
        return ResponseEntity.created(uri).body(cargoCadastrado);
    }

    @PutMapping(CARGOS_ID_ENDPOINT)
    @Transactional
    public ResponseEntity<Cargo> atualizar(@PathVariable Long id,
                                       @RequestBody @Valid Cargo cargo) {

        return ResponseEntity.ok(service.atualizar(id, cargo));
    }

    @DeleteMapping(CARGOS_ID_ENDPOINT)
    @Transactional
    public ResponseEntity deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
