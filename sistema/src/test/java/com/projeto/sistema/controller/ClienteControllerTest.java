package com.projeto.sistema.controller;

import com.projeto.sistema.dto.ClienteRegisterDto;
import com.projeto.sistema.dto.ClienteUpdateDTO;
import com.projeto.sistema.dto.MensagemResponseDto;
import com.projeto.sistema.model.Cliente;
import com.projeto.sistema.service.ServiceCliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteControllerTest {

    @Mock
    private ServiceCliente serviceCliente;

    @InjectMocks
    private ClienteController clienteController;

    private Cliente cliente;
    private ClienteRegisterDto clienteRegisterDto;
    private ClienteUpdateDTO clienteUpdateDTO;

    @BeforeEach
    void setUp() {
        cliente = new Cliente(1L, "João Silva", "joao@email.com", "12345678900", "11999999999");
        
        clienteRegisterDto = new ClienteRegisterDto();
        clienteRegisterDto.setNome("João Silva");
        clienteRegisterDto.setEmail("joao@email.com");
        clienteRegisterDto.setCpf("12345678900");
        clienteRegisterDto.setTelefone("11999999999");
        
        clienteUpdateDTO = new ClienteUpdateDTO();
        clienteUpdateDTO.setNome("João Santos");
        clienteUpdateDTO.setTelefone("11888888888");
    }

    // @Test
    // @DisplayName("Deve salvar cliente com sucesso")
    // void salvar_DeveRetornarClienteSalvo_QuandoDadosValidos() {
    //     // Arrange
    //     when(serviceCliente.convertRegisterDtoToEntity(any(ClienteRegisterDto.class)))
    //             .thenReturn(cliente);
    //     when(serviceCliente.salvar(any(Cliente.class)))
    //             .thenReturn(cliente);

    //     // Act
    //     ResponseEntity<MensagemResponseDto> response = clienteController.salvar(clienteRegisterDto);

    //     // Assert
    //     assertNotNull(response);
    //     assertEquals(HttpStatus.OK, response.getStatusCode());
    //     assertNotNull(response.getBody());
    //     assertEquals("Cliente criado com sucesso", response.getBody().getMensagem());
    //     assertEquals("200", response.getBody().getCodigo());
    //     assertEquals(cliente, response.getBody().getDados());

    //     verify(serviceCliente).convertRegisterDtoToEntity(clienteRegisterDto);
    //     verify(serviceCliente).salvar(cliente);
    // }

    // @Test
    // @DisplayName("Deve retornar erro ao salvar cliente com exceção")
    // void salvar_DeveRetornarErro_QuandoOcorrerExcecao() {
    //     // Arrange
    //     when(serviceCliente.convertRegisterDtoToEntity(any(ClienteRegisterDto.class)))
    //             .thenThrow(new RuntimeException("Erro de banco de dados"));

    //     // Act
    //     ResponseEntity<MensagemResponseDto> response = clienteController.salvar(clienteRegisterDto);

    //     // Assert
    //     assertNotNull(response);
    //     assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    //     assertNotNull(response.getBody());
    //     assertEquals("Erro ao criar cliente", response.getBody().getMensagem());
    //     assertEquals("400", response.getBody().getCodigo());

    //     verify(serviceCliente).convertRegisterDtoToEntity(clienteRegisterDto);
    //     verify(serviceCliente, never()).salvar(any(Cliente.class));
    // }

    @Test
    @DisplayName("Deve listar todos os clientes")
    void listar_DeveRetornarListaDeClientes() {
        // Arrange
        List<Cliente> clientes = Arrays.asList(
            cliente,
            new Cliente(2L, "Maria Souza", "maria@email.com", "98765432100", "11777777777")
        );
        when(serviceCliente.listarTodos()).thenReturn(clientes);

        // Act
        ResponseEntity<Iterable<Cliente>> response = clienteController.listar();

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, ((List<Cliente>) response.getBody()).size());

        verify(serviceCliente).listarTodos();
    }

    @Test
    @DisplayName("Deve buscar cliente por ID existente")
    void buscarPorId_DeveRetornarCliente_QuandoIdExistir() {
        // Arrange
        when(serviceCliente.buscarPorId(1L)).thenReturn(Optional.of(cliente));

        // Act
        ResponseEntity<Cliente> response = clienteController.buscarPorId(1L);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(cliente.getId(), response.getBody().getId());
        assertEquals(cliente.getNome(), response.getBody().getNome());

        verify(serviceCliente).buscarPorId(1L);
    }

    @Test
    @DisplayName("Deve retornar 404 ao buscar cliente por ID inexistente")
    void buscarPorId_DeveRetornarNotFound_QuandoIdNaoExistir() {
        // Arrange
        when(serviceCliente.buscarPorId(1L)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Cliente> response = clienteController.buscarPorId(1L);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());

        verify(serviceCliente).buscarPorId(1L);
    }

    @Test
    @DisplayName("Deve atualizar cliente com sucesso")
    void atualizar_DeveRetornarClienteAtualizado_QuandoIdExistir() {
        // Arrange
        Cliente clienteAtualizado = new Cliente(1L, "João Santos", "joao@email.com", "12345678900", "11888888888");
        when(serviceCliente.atualizar(eq(1L), any(ClienteUpdateDTO.class)))
                .thenReturn(Optional.of(clienteAtualizado));

        // Act
        ResponseEntity<Cliente> response = clienteController.atualizar(1L, clienteUpdateDTO);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("João Santos", response.getBody().getNome());
        assertEquals("11888888888", response.getBody().getTelefone());

        verify(serviceCliente).atualizar(1L, clienteUpdateDTO);
    }

    @Test
    @DisplayName("Deve retornar 404 ao atualizar cliente inexistente")
    void atualizar_DeveRetornarNotFound_QuandoIdNaoExistir() {
        // Arrange
        when(serviceCliente.atualizar(eq(1L), any(ClienteUpdateDTO.class)))
                .thenReturn(Optional.empty());

        // Act
        ResponseEntity<Cliente> response = clienteController.atualizar(1L, clienteUpdateDTO);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());

        verify(serviceCliente).atualizar(1L, clienteUpdateDTO);
    }

    @Test
    @DisplayName("Deve deletar cliente com sucesso")
    void deletar_DeveRetornarNoContent_QuandoClienteExistir() {
        // Arrange
        when(serviceCliente.deletar(1L)).thenReturn(true);

        // Act
        ResponseEntity<Void> response = clienteController.deletar(1L);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        verify(serviceCliente).deletar(1L);
    }

    @Test
    @DisplayName("Deve retornar 404 ao deletar cliente inexistente")
    void deletar_DeveRetornarNotFound_QuandoClienteNaoExistir() {
        // Arrange
        when(serviceCliente.deletar(1L)).thenReturn(false);

        // Act
        ResponseEntity<Void> response = clienteController.deletar(1L);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(serviceCliente).deletar(1L);
    }

    @Test
    @DisplayName("Deve verificar interações com dependências")
    void shouldVerifyDependencies() {
        // Este teste verifica se todas as dependências estão sendo injetadas corretamente
        assertNotNull(clienteController);
        assertNotNull(serviceCliente);
    }
}