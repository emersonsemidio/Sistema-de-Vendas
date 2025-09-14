package com.projeto.sistema.controller;

import com.projeto.sistema.dto.CompraRegisterDto;
import com.projeto.sistema.dto.MensagemResponseDto;
import com.projeto.sistema.dto.ProdutoRegisterDto;
import com.projeto.sistema.model.Compra;
import com.projeto.sistema.model.Produto;
import com.projeto.sistema.service.ServiceCliente;
import com.projeto.sistema.service.ServiceCompra;
import com.projeto.sistema.service.ServiceUsuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.security.Provider.Service;
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
    public ResponseEntity<MensagemResponseDto> salvar(@RequestBody @Valid CompraRegisterDto compraDto) {
        try {
            Compra novaCompra = serviceCompra.convertRegisterDtoToEntity(compraDto);
            Compra salva = serviceCompra.salvar(novaCompra);
            MensagemResponseDto mensagem = new MensagemResponseDto("Compra criada com sucesso", "200", salva);
            return ResponseEntity.ok(mensagem);

        } catch (Exception e) {
            MensagemResponseDto errorResponse = new MensagemResponseDto(
                "Erro ao criar compra", "400", e.getMessage()
            );
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
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