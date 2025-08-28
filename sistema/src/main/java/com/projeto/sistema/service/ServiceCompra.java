package com.projeto.sistema.service;

import com.projeto.sistema.model.Compras;
import com.projeto.sistema.repo.RepoCompra;
import com.projeto.sistema.repo.RepoCliente;
import com.projeto.sistema.repo.RepoUsuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ServiceCompra {

    @Autowired
    private RepoCompra repoCompra;

    @Autowired
    private RepoUsuario repoUsuario;

    @Autowired
    private RepoCliente repoCliente;

    public Iterable<Compras> listarTodas() {
        return repoCompra.findAll();
    }

    public Optional<Compras> buscarPorId(Long id) {
        return repoCompra.findById(id);
    }

    public Compras salvar(Compras venda) {
    boolean clienteExiste = repoCliente.existsById(venda.getClienteId());
    boolean usuarioExiste = repoUsuario.existsById(venda.getUsuarioId());

    if (!clienteExiste && !usuarioExiste) {
        throw new IllegalArgumentException("Cliente com ID " + venda.getClienteId() + " e Usuário com ID " + venda.getUsuarioId() + " não existem.");
    } else if (!clienteExiste) {
        throw new IllegalArgumentException("Cliente com ID " + venda.getClienteId() + " não existe.");
    } else if (!usuarioExiste) {
        throw new IllegalArgumentException("Usuário com ID " + venda.getUsuarioId() + " não existe.");
    }

    return repoCompra.save(venda);
}


    public Optional<Compras> atualizar(Long id, Compras novaCompra) {
        return repoCompra.findById(id).map(compraExistente -> {
            compraExistente.setClienteId(novaCompra.getClienteId());
            compraExistente.setTotal(novaCompra.getTotal());
            compraExistente.setUsuarioId(novaCompra.getUsuarioId());
            compraExistente.setFormaPagamento(novaCompra.getFormaPagamento());
            compraExistente.setStatus(novaCompra.getStatus());
            return repoCompra.save(compraExistente);
        });
    }

    public boolean deletar(Long id) {
        if (repoCompra.existsById(id)) {
            repoCompra.deleteById(id);
            return true;
        }
        return false;
    }
}
