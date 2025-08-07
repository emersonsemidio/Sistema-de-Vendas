package com.projeto.sistema.service;

import com.projeto.sistema.model.Venda;
import com.projeto.sistema.repo.RepoVenda;
import com.projeto.sistema.repo.RepoCliente;
import com.projeto.sistema.repo.RepoUsuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceVenda {

    @Autowired
    private RepoVenda repoVenda;

    @Autowired
    private RepoUsuario repoUsuario;

    @Autowired
    private RepoCliente repoCliente;

    public Iterable<Venda> listarTodas() {
        return repoVenda.findAll();
    }

    public Optional<Venda> buscarPorId(Long id) {
        return repoVenda.findById(id);
    }

    public Venda salvar(Venda venda) {
    boolean clienteExiste = repoCliente.existsById(venda.getClienteId());
    boolean usuarioExiste = repoUsuario.existsById(venda.getUsuarioId());

    if (!clienteExiste && !usuarioExiste) {
        throw new IllegalArgumentException("Cliente com ID " + venda.getClienteId() + " e Usuário com ID " + venda.getUsuarioId() + " não existem.");
    } else if (!clienteExiste) {
        throw new IllegalArgumentException("Cliente com ID " + venda.getClienteId() + " não existe.");
    } else if (!usuarioExiste) {
        throw new IllegalArgumentException("Usuário com ID " + venda.getUsuarioId() + " não existe.");
    }

    return repoVenda.save(venda);
}


    public Optional<Venda> atualizar(Long id, Venda novaVenda) {
        return repoVenda.findById(id).map(vendaExistente -> {
            vendaExistente.setClienteId(novaVenda.getClienteId());
            vendaExistente.setTotal(novaVenda.getTotal());
            vendaExistente.setUsuarioId(novaVenda.getUsuarioId());
            vendaExistente.setFormaPagamento(novaVenda.getFormaPagamento());
            vendaExistente.setStatus(novaVenda.getStatus());
            return repoVenda.save(vendaExistente);
        });
    }

    public boolean deletar(Long id) {
        if (repoVenda.existsById(id)) {
            repoVenda.deleteById(id);
            return true;
        }
        return false;
    }
}
