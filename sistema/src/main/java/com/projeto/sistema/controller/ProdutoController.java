package com.projeto.sistema.controller;

import com.projeto.sistema.model.Produto;
import com.projeto.sistema.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/produtos")
@Tag(name = "Produtos", description = "Operações de CRUD para gerenciamento de produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    @Operation(
        summary = "Listar todos os produtos",
        description = "Retorna uma lista com todos os produtos cadastrados no sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Produtos listados com sucesso",
            content = @Content(mediaType = "application/json", 
                             schema = @Schema(implementation = Produto.class))
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Erro interno no servidor"
        )
    })
    public Iterable<Produto> listarTodos() {
        return produtoService.listarTodos();
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Buscar produto por ID",
        description = "Retorna um produto específico baseado no ID fornecido"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Produto encontrado com sucesso",
            content = @Content(mediaType = "application/json", 
                             schema = @Schema(implementation = Produto.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Produto não encontrado"
        )
    })
    public ResponseEntity<Produto> buscarPorId(@PathVariable Long id) {
        Produto produto = produtoService.buscarPorId(id);
        if (produto != null) {
            return ResponseEntity.ok(produto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(
        summary = "Criar novo produto",
        description = "Cria um novo produto no sistema com validação dos dados"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Produto criado com sucesso",
            content = @Content(mediaType = "application/json", 
                             schema = @Schema(implementation = Produto.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Dados inválidos fornecidos"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Erro interno no servidor"
        )
    })
    public ResponseEntity<Produto> criarProduto(
        @RequestBody(
            description = "Dados do produto a ser criado",
            required = true,
            content = @Content(schema = @Schema(implementation = Produto.class))
        )
        @Valid @org.springframework.web.bind.annotation.RequestBody Produto produto) {
        Produto novoProduto = produtoService.salvar(produto);
        return ResponseEntity.ok(novoProduto);
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Atualizar produto existente",
        description = "Atualiza os dados de um produto existente baseado no ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Produto atualizado com sucesso",
            content = @Content(mediaType = "application/json", 
                             schema = @Schema(implementation = Produto.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Produto não encontrado"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Dados inválidos fornecidos"
        )
    })
    public ResponseEntity<Produto> atualizarProduto(
        @PathVariable Long id,
        @RequestBody(
            description = "Novos dados do produto",
            required = true,
            content = @Content(schema = @Schema(implementation = Produto.class))
        )
        @Valid @org.springframework.web.bind.annotation.RequestBody Produto produto) {
        Produto atualizado = produtoService.atualizar(id, produto);
        if (atualizado != null) {
            return ResponseEntity.ok(atualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Deletar produto",
        description = "Remove um produto do sistema baseado no ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "204",
            description = "Produto deletado com sucesso"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Produto não encontrado"
        )
    })
    public ResponseEntity<Void> deletarProduto(@PathVariable Long id) {
        boolean deletado = produtoService.deletar(id);
        if (deletado) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}