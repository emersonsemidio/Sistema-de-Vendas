package com.projeto.sistema.service;

import com.projeto.sistema.model.Cliente;
import com.projeto.sistema.repo.RepoCliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ServiceCliente {

    @Autowired
    private RepoCliente repoCliente;

    // Criar ou atualizar
    public Cliente salvar(Cliente cliente) {
        return repoCliente.save(cliente);
    }

    // Buscar todos
    public Iterable<Cliente> listarTodos() {
        return repoCliente.findAll();
    }

    // Buscar por ID
    public Optional<Cliente> buscarPorId(Long id) {
        return repoCliente.findById(id);
    }

    // Atualizar (só salva se o ID já existir)
    public Optional<Cliente> atualizar(Long id, Cliente clienteAtualizado) {
        return repoCliente.findById(id).map(clienteExistente -> {
            clienteAtualizado.setId(id); // garante que o ID se mantém
            return repoCliente.save(clienteAtualizado);
        });
    }

    // Remover
    public boolean deletar(Long id) {
        if (repoCliente.existsById(id)) {
            repoCliente.deleteById(id);
            return true;
        }
        return false;
    }
}
