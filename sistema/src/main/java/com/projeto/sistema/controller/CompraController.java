package com.projeto.sistema.controller;

import com.projeto.sistema.model.Compra;
import com.projeto.sistema.service.ServiceCompra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Optional;

@RestController
@RequestMapping("/compra")
@Tag(name = "Compras", description = "Operações de CRUD para gerenciamento de compras")
public class CompraController {

    @Autowired
    private ServiceCompra serviceCompra;

    @GetMapping
    @Operation(summary = "Listar todas as compras")
    @ApiResponse(responseCode = "200", description = "Compras listadas com sucesso")
    public ResponseEntity<Iterable<Compra>> listarTodas() {
        return ResponseEntity.ok(serviceCompra.listarTodas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar compra por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Compra encontrada"),
        @ApiResponse(responseCode = "404", description = "Compra não encontrada")
    })
    public ResponseEntity<Compra> buscarPorId(@PathVariable Long id) {
        Optional<Compra> compra = serviceCompra.buscarPorId(id);
        return compra.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Criar nova compra")
    @ApiResponse(responseCode = "200", description = "Compra criada com sucesso")
    public ResponseEntity<Compra> criar(@RequestBody Compra compra) {
        Compra compraNova = serviceCompra.salvar(compra);
        return ResponseEntity.ok(compraNova);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar compra")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Compra atualizada"),
        @ApiResponse(responseCode = "404", description = "Compra não encontrada")
    })
    public ResponseEntity<Compra> atualizar(@PathVariable Long id, @RequestBody Compra novaCompra) {
        return serviceCompra.atualizar(id, novaCompra)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar compra")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Compra deletada"),
        @ApiResponse(responseCode = "404", description = "Compra não encontrada")
    })
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (serviceCompra.deletar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}