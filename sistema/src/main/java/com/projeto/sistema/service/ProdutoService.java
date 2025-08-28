package com.projeto.sistema.service;

import com.projeto.sistema.model.Produto;
import com.projeto.sistema.repo.RepoProduto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private RepoProduto produtoRepository;

    // Listar todos os produtos
    public Iterable<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    // Buscar produto por ID
    public Produto buscarPorId(Long id) {
        return produtoRepository.findById(id).orElse(null);
    }

    // Criar novo produto
    public Produto salvar(Produto produto) {
        return produtoRepository.save(produto);
    }

    // Atualizar produto existente
    public Produto atualizar(Long id, Produto produtoAtualizado) {
        Optional<Produto> optionalProduto = produtoRepository.findById(id);
        if (optionalProduto.isPresent()) {
            Produto existente = optionalProduto.get();
            existente.setNome(produtoAtualizado.getNome());
            existente.setDescricao(produtoAtualizado.getDescricao());
            existente.setPreco(produtoAtualizado.getPreco());
            existente.setQuantidade(produtoAtualizado.getQuantidade());
            return produtoRepository.save(existente);
        } else {
            return null;
        }
    }

    // Deletar produto por ID
    public boolean deletar(Long id) {
        if (produtoRepository.existsById(id)) {
            produtoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
