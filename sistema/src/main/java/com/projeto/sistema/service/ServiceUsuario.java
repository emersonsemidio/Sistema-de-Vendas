package com.projeto.sistema.service;

import com.projeto.sistema.dto.ClienteRegisterDto;
import com.projeto.sistema.dto.ClienteUpdateDTO;
import com.projeto.sistema.dto.UsuarioRegisterDto;
import com.projeto.sistema.dto.UsuarioUpdateDto;
import com.projeto.sistema.model.Cliente;
import com.projeto.sistema.model.Usuario;
import com.projeto.sistema.repo.RepoUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ServiceUsuario {

    @Autowired
    private RepoUsuario usuarioRepository;

    public Usuario login(String email, String senha) {
    Optional<Usuario> usuario = usuarioRepository.findByEmail(email);

    if (usuario.isPresent() && usuario.get().getSenha().equals(senha)) {
        return usuario.get(); // ou gerar token, etc
    } else {
        throw new RuntimeException("Email ou senha inválidos");
    }
}


    public Iterable<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario criar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> atualizar(Long id, UsuarioUpdateDto dto) {
        return usuarioRepository.findById(id).map(usuarioExistente -> {

            if (dto.getNome() != null) {
                usuarioExistente.setNome(dto.getNome());
            }
            if (dto.getEmail() != null) {
                usuarioExistente.setEmail(dto.getEmail());
            }
            if (dto.getSenha() != null) {
                usuarioExistente.setSenha(dto.getSenha());
            }

            return usuarioRepository.save(usuarioExistente);
        });
    }

    public Usuario convertUpdateDtoToEntity(UsuarioUpdateDto dto) {
        Usuario usuario = new Usuario();
        // Apenas seta os campos que vieram no DTO
        usuario.setNome(dto.getNome()); // Pode ser null - não problema
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());
        return usuario;
    }

    public Usuario convertRegisterDtoToEntity(UsuarioRegisterDto dto) {
        Usuario usuario = new Usuario();
        // Apenas seta os campos que vieram no DTO
        usuario.setNome(dto.getNome()); // Pode ser null - não problema
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());
        return usuario;
    }

    public boolean deletar(Long id) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuarioRepository.deleteById(id);
            return true;
        }).orElse(false);
    }
}
