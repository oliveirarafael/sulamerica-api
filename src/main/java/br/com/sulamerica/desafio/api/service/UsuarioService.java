package br.com.sulamerica.desafio.api.service;

import br.com.sulamerica.desafio.api.exception.ConflictException;
import br.com.sulamerica.desafio.api.exception.NotFoundException;
import br.com.sulamerica.desafio.api.model.entity.Perfil;
import br.com.sulamerica.desafio.api.model.entity.Usuario;
import br.com.sulamerica.desafio.api.repository.UsuarioRepository;
import br.com.sulamerica.desafio.api.repository.specification.UsuarioSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> getUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario getUsuarioPorId(Long id) {
        Optional<Usuario> usuarioConsultado = usuarioRepository.findById(id);

        if (usuarioConsultado.isPresent()) {
            return usuarioConsultado.get();
        }

        throw new NotFoundException("Usuário não foi encontrado");
    }

    public Usuario salvar(Usuario usuario) {
        Optional<Usuario> usuarioCadastrado = usuarioRepository.findByNomeAndCpf(usuario.getUsername(), usuario.getCpf());

        if(usuarioCadastrado.isEmpty())
            return usuarioRepository.save(usuario);

        throw new ConflictException("Usuário "+usuario.getUsername()+" já cadastrado");
    }

    public void deletar(Long id) {
        Usuario usuario = getUsuarioPorId(id);
        usuarioRepository.delete(usuario);
    }

    public Usuario atualizar(Long id, Usuario usuario) {
        Optional<Usuario> usuarioCadastrado = usuarioRepository.findByNomeAndCpf(usuario.getUsername(), usuario.getCpf());

        if(usuarioCadastrado.isPresent()){
            throw new ConflictException("já possui usuário cadastrado com mesmo cpf ("+usuario.getCpf()+") " +
                                        "e nome ("+usuario.getUsername()+")");
        }

        Usuario usuarioAtualizado = getUsuarioPorId(id);
        usuarioAtualizado.setUsername(usuario.getUsername());
        usuarioAtualizado.setCpf(usuario.getCpf());
        usuarioAtualizado.setDataNascimento(usuario.getDataNascimento());
        usuarioAtualizado.setSexo(usuario.getSexo());
        usuarioAtualizado.setCargo(usuario.getCargo());
        usuarioAtualizado.setAuthorities((List<Perfil>) usuario.getAuthorities());
        usuarioRepository.save(usuarioAtualizado);

        return usuarioAtualizado;
    }

    public List<Usuario> getUsuarioCpfComecaComZero(){
        List<Usuario> usuarios = usuarioRepository.findCpfsComecaZero();

        if(usuarios.isEmpty()){
            throw new NotFoundException("Nenhum usuario com CPF que comeca com zero foi encontrado");
        }

        return usuarios;
    }

    public List<Usuario> getUsuarios(String nome, String cpf, boolean status){
        List<Usuario> usuarios = usuarioRepository.findAll(Specification
                                                  .where(UsuarioSpecification.nome(nome))
                                                  .or(UsuarioSpecification.cpf(cpf))
                                                  .or(UsuarioSpecification.status(status))
        );

        if(usuarios.isEmpty()){
            throw new NotFoundException("Usuário não foi encontrado");
        }

        return usuarios;
    }


}
