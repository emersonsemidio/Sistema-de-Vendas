package com.projeto.sistema.service;
import com.projeto.sistema.model.ItemCarrinho;
import com.projeto.sistema.model.Produto;
import com.projeto.sistema.repo.RepoItemCarrinho;
import com.projeto.sistema.repo.RepoProduto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class CarrinhoService {

    @Autowired
    private RepoItemCarrinho carrinhoRepository;

    @Autowired
    private RepoProduto produtoRepository;

    public ItemCarrinho adicionarItem(ItemCarrinho item) {
        // Valida se o produto existe e tem estoque
        Produto produto = produtoRepository.findById(item.getProduto().getId())
            .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        if (produto.getQuantidade() < item.getQuantidade()) {
            throw new RuntimeException("Estoque insuficiente");
        }

        return carrinhoRepository.save(item);
    }
}