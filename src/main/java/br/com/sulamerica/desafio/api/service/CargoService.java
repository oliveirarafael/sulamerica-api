package br.com.sulamerica.desafio.api.service;

import br.com.sulamerica.desafio.api.exception.ConflictException;
import br.com.sulamerica.desafio.api.exception.NotFoundException;
import br.com.sulamerica.desafio.api.model.entity.Cargo;
import br.com.sulamerica.desafio.api.repository.CargoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CargoService {
    @Autowired
    private CargoRepository cargoRepository;

    public List<Cargo> getCargos() {
        return cargoRepository.findAll();
    }

    public Cargo getCargoPorId(Long id) {
        Optional<Cargo> cargoConsultado = cargoRepository.findById(id);

        if (cargoConsultado.isPresent()) {
            return cargoConsultado.get();
        }

        throw new NotFoundException("Cargo não foi encontrado");
    }

    public Cargo salvar(Cargo cargo) {
        Optional<Cargo> cargoConsultado = cargoRepository.findByNome(cargo.getNome());

        if(cargoConsultado.isEmpty())
            return cargoRepository.save(cargo);

        throw new ConflictException("Cargo "+cargo.getNome()+" já cadastrado");
    }

    public void deletar(Long id) {
        Cargo cargo = getCargoPorId(id);
        cargoRepository.delete(cargo);
    }

    public Cargo atualizar(Long id, Cargo cargo) {
        Optional<Cargo> cargoCadastrado = cargoRepository.findByNome(cargo.getNome());

        if(cargoCadastrado.isPresent()){
            throw new ConflictException("já possui cargo cadastrado com mesmo nome ("+cargo.getNome()+") ");
        }

        Cargo cargoAtualizado = getCargoPorId(id);
        cargoAtualizado.setNome(cargo.getNome());
        cargoRepository.save(cargoAtualizado);

        return cargoAtualizado;
    }

}
