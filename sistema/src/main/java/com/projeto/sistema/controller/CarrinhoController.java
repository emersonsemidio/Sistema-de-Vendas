package com.projeto.sistema.controller;
import com.projeto.sistema.model.ItemCarrinho;
import com.projeto.sistema.repo.RepoItemCarrinho;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/carrinho")
public class CarrinhoController {

    @Autowired
    private RepoItemCarrinho repository;

    // Adiciona item ao carrinho
    @PostMapping
    public ResponseEntity<ItemCarrinho> adicionarItem(@RequestBody ItemCarrinho item) {
        ItemCarrinho itemSalvo = repository.save(item);
        return ResponseEntity.ok(itemSalvo);
    }

    // Lista itens do carrinho
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<ItemCarrinho>> listarItens(@PathVariable Long usuarioId) {
        List<ItemCarrinho> itens = repository.findByUsuarioId(usuarioId);
        return ResponseEntity.ok(itens);
    }

    // Remove item do carrinho
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerItem(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}