package com.projeto.sistema.controller;

import com.projeto.sistema.model.Venda;
import com.projeto.sistema.service.ServiceVenda;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/venda")
public class VendaController {

    @Autowired
    private ServiceVenda serviceVenda;

    @GetMapping
    public ResponseEntity<Iterable<Venda>> listarTodas() {
        return ResponseEntity.ok(serviceVenda.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venda> buscarPorId(@PathVariable Long id) {
        Optional<Venda> venda = serviceVenda.buscarPorId(id);
        return venda.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Venda> criar(@RequestBody Venda venda) {
        Venda vendaNova = serviceVenda.salvar(venda);
        return ResponseEntity.ok(vendaNova);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Venda> atualizar(@PathVariable Long id, @RequestBody Venda novaVenda) {
        return serviceVenda.atualizar(id, novaVenda)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (serviceVenda.deletar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
