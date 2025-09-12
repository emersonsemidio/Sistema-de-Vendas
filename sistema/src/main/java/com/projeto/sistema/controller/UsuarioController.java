package com.projeto.sistema.controller;

import com.projeto.sistema.dto.ClienteRegisterDto;
import com.projeto.sistema.dto.MensagemResponseDto;
import com.projeto.sistema.dto.UsuarioRegisterDto;
import com.projeto.sistema.dto.UsuarioUpdateDto;
import com.projeto.sistema.model.Cliente;
import com.projeto.sistema.model.Usuario;
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

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuários", description = "Operações de CRUD para gerenciamento de usuários")
public class UsuarioController {

    @Autowired
    private ServiceUsuario usuarioService;

    @GetMapping
    @Operation(summary = "Listar todos os usuários")
    @ApiResponse(responseCode = "200", description = "Usuários listados com sucesso")
    public ResponseEntity<Iterable<Usuario>> listarTodos() {
        return ResponseEntity.ok(usuarioService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {
        return usuarioService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Criar novo usuário")
    @ApiResponse(responseCode = "200", description = "Usuário criado com sucesso")
    public ResponseEntity<MensagemResponseDto> salvar(@RequestBody UsuarioRegisterDto usuarioDto) {
        System.out.println("Recebido usuário: " + usuarioDto.toString());
        try {
            Usuario usuario = usuarioService.convertRegisterDtoToEntity(usuarioDto);
            Usuario salvo = usuarioService.criar(usuario);
            MensagemResponseDto mensagem = new MensagemResponseDto("Usuário criado com sucesso", "200", salvo);
            return ResponseEntity.ok(mensagem);

        } catch (Exception e) {
            MensagemResponseDto errorResponse = new MensagemResponseDto(
                "Erro ao criar usuário", "400", e.getMessage()
            );
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar usuário")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário atualizado"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<Usuario> atualizar(@PathVariable Long id, @RequestBody @Valid UsuarioUpdateDto usuarioDto) {
        return usuarioService.atualizar(id, usuarioDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar usuário")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Usuário deletado"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (usuarioService.deletar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}