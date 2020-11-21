package br.com.sulamerica.desafio.api.service;

import br.com.sulamerica.desafio.api.exception.ConflictException;
import br.com.sulamerica.desafio.api.exception.NotFoundException;
import br.com.sulamerica.desafio.api.model.entity.Perfil;
import br.com.sulamerica.desafio.api.repository.PerfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PerfilService {

    @Autowired
    private PerfilRepository perfilRepository;

    public List<Perfil> getPerfis() {
        return perfilRepository.findAll();
    }

    public Perfil getPerfilPorId(Long id) {
        Optional<Perfil> perfilConsultado = perfilRepository.findById(id);

        if (perfilConsultado.isPresent()) {
            return perfilConsultado.get();
        }

        throw new NotFoundException("Perfil não foi encontrado");
    }

    public Perfil salvar(Perfil perfil) {
        Optional<Perfil> perfilCadastrado = perfilRepository.findByNome(perfil.getNome());

        if(perfilCadastrado.isEmpty())
            return perfilRepository.save(perfil);

        throw new ConflictException("Perfil "+perfil.getNome()+" já cadastrado");
    }

    public void deletar(Long id) {
        Perfil perfil = getPerfilPorId(id);
        perfilRepository.delete(perfil);
    }

    public Perfil atualizar(Long id, Perfil perfil) {
        Optional<Perfil> perfilCadastrado = perfilRepository.findByNome(perfil.getNome());

        if(perfilCadastrado.isPresent()){
            throw new ConflictException("já possui perfil cadastrado com mesmo nome ("+perfil.getNome()+") ");
        }

        Perfil perfilAtualizado = getPerfilPorId(id);
        perfilAtualizado.setNome(perfil.getNome());
        perfilRepository.save(perfilAtualizado);

        return perfilAtualizado;
    }


}
