package com.projeto.sistema.service;

import com.projeto.sistema.dto.ClienteRegisterDto;
import com.projeto.sistema.dto.ClienteUpdateDTO;
import com.projeto.sistema.model.Cliente;
import com.projeto.sistema.repo.RepoCliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private RepoCliente repoCliente;

    @InjectMocks
    private ServiceCliente serviceCliente;

    private Cliente cliente;
    private ClienteRegisterDto clienteRegisterDto;
    private ClienteUpdateDTO clienteUpdateDTO;

    @BeforeEach
    void setUp() {
        cliente = new Cliente(1L, "João Silva", "joao@email.com", "12345678900", "11999999999");
        
        clienteRegisterDto = new ClienteRegisterDto();
        clienteRegisterDto.setNome("Maria Souza");
        clienteRegisterDto.setEmail("maria@email.com");
        clienteRegisterDto.setCpf("98765432100");
        clienteRegisterDto.setTelefone("11888888888");
        clienteRegisterDto.setEndereco("Rua Teste, 123");
        
        clienteUpdateDTO = new ClienteUpdateDTO();
        clienteUpdateDTO.setNome("João Santos");
        clienteUpdateDTO.setEmail("joao.santos@email.com");
        clienteUpdateDTO.setTelefone("11777777777");
        clienteUpdateDTO.setEndereco("Avenida Nova, 456");
    }

    @Test
    @DisplayName("Deve salvar cliente com sucesso")
    void salvar_DeveRetornarClienteSalvo_QuandoDadosValidos() {
        // Arrange
        when(repoCliente.save(any(Cliente.class))).thenReturn(cliente);

        // Act
        Cliente resultado = serviceCliente.salvar(cliente);

        // Assert
        assertNotNull(resultado);
        assertEquals(cliente.getId(), resultado.getId());
        assertEquals(cliente.getNome(), resultado.getNome());
        verify(repoCliente).save(cliente);
    }

    @Test
    @DisplayName("Deve listar todos os clientes")
    void listarTodos_DeveRetornarListaDeClientes() {
        // Arrange
        List<Cliente> clientes = Arrays.asList(
            cliente,
            new Cliente(2L, "Maria Souza", "maria@email.com", "98765432100", "11777777777")
        );
        when(repoCliente.findAll()).thenReturn(clientes);

        // Act
        Iterable<Cliente> resultado = serviceCliente.listarTodos();

        // Assert
        assertNotNull(resultado);
        assertEquals(2, ((List<Cliente>) resultado).size());
        verify(repoCliente).findAll();
    }

    @Test
    @DisplayName("Deve buscar cliente por ID existente")
    void buscarPorId_DeveRetornarCliente_QuandoIdExistir() {
        // Arrange
        when(repoCliente.findById(1L)).thenReturn(Optional.of(cliente));

        // Act
        Optional<Cliente> resultado = serviceCliente.buscarPorId(1L);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals(cliente.getId(), resultado.get().getId());
        assertEquals(cliente.getNome(), resultado.get().getNome());
        verify(repoCliente).findById(1L);
    }

    @Test
    @DisplayName("Deve retornar vazio ao buscar cliente por ID inexistente")
    void buscarPorId_DeveRetornarVazio_QuandoIdNaoExistir() {
        // Arrange
        when(repoCliente.findById(999L)).thenReturn(Optional.empty());

        // Act
        Optional<Cliente> resultado = serviceCliente.buscarPorId(999L);

        // Assert
        assertFalse(resultado.isPresent());
        verify(repoCliente).findById(999L);
    }

    @Test
    @DisplayName("Deve atualizar cliente existente com todos os campos")
    void atualizar_DeveRetornarClienteAtualizado_QuandoIdExistirECamposPreenchidos() {
        // Arrange
        Cliente clienteExistente = new Cliente(1L, "João Silva", "joao@email.com", "12345678900", "11999999999");
        when(repoCliente.findById(1L)).thenReturn(Optional.of(clienteExistente));
        when(repoCliente.save(any(Cliente.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Optional<Cliente> resultado = serviceCliente.atualizar(1L, clienteUpdateDTO);

        // Assert
        assertTrue(resultado.isPresent());
        Cliente clienteAtualizado = resultado.get();
        
        assertEquals("João Santos", clienteAtualizado.getNome());
        assertEquals("joao.santos@email.com", clienteAtualizado.getEmail());
        assertEquals("11777777777", clienteAtualizado.getTelefone());
        assertEquals("Avenida Nova, 456", clienteAtualizado.getEndereco());
        assertEquals("12345678900", clienteAtualizado.getCpf()); // CPF não deve mudar
        
        verify(repoCliente).findById(1L);
        verify(repoCliente).save(clienteExistente);
    }

    @Test
    @DisplayName("Deve atualizar cliente existente com campos parciais")
    void atualizar_DeveAtualizarApenasCamposPreenchidos_QuandoAlgunsCamposNull() {
        // Arrange
        Cliente clienteExistente = new Cliente(1L, "João Silva", "joao@email.com", "12345678900", "11999999999");
        ClienteUpdateDTO updateParcial = new ClienteUpdateDTO();
        updateParcial.setNome("João Santos"); // Apenas nome é atualizado
        updateParcial.setTelefone(null); // Telefone permanece o mesmo
        updateParcial.setEmail(null); // Email permanece o mesmo
        
        when(repoCliente.findById(1L)).thenReturn(Optional.of(clienteExistente));
        when(repoCliente.save(any(Cliente.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Optional<Cliente> resultado = serviceCliente.atualizar(1L, updateParcial);

        // Assert
        assertTrue(resultado.isPresent());
        Cliente clienteAtualizado = resultado.get();
        
        assertEquals("João Santos", clienteAtualizado.getNome()); // Nome atualizado
        assertEquals("joao@email.com", clienteAtualizado.getEmail()); // Email original
        assertEquals("11999999999", clienteAtualizado.getTelefone()); // Telefone original
        
        verify(repoCliente).findById(1L);
        verify(repoCliente).save(clienteExistente);
    }

    @Test
    @DisplayName("Deve retornar vazio ao tentar atualizar cliente inexistente")
    void atualizar_DeveRetornarVazio_QuandoIdNaoExistir() {
        // Arrange
        when(repoCliente.findById(999L)).thenReturn(Optional.empty());

        // Act
        Optional<Cliente> resultado = serviceCliente.atualizar(999L, clienteUpdateDTO);

        // Assert
        assertFalse(resultado.isPresent());
        verify(repoCliente).findById(999L);
        verify(repoCliente, never()).save(any(Cliente.class));
    }

    @Test
    @DisplayName("Deve converter ClienteRegisterDto para entidade Cliente")
    void convertRegisterDtoToEntity_DeveConverterCorretamente() {
        // Act
        Cliente resultado = serviceCliente.convertRegisterDtoToEntity(clienteRegisterDto);

        // Assert
        assertNotNull(resultado);
        assertEquals("Maria Souza", resultado.getNome());
        assertEquals("maria@email.com", resultado.getEmail());
        assertEquals("98765432100", resultado.getCpf());
        assertEquals("11888888888", resultado.getTelefone());
        assertEquals("Rua Teste, 123", resultado.getEndereco());
    }

    @Test
    @DisplayName("Deve converter ClienteUpdateDTO para entidade Cliente")
    void convertUpdateDtoToEntity_DeveConverterCorretamente() {
        // Act
        Cliente resultado = serviceCliente.convertUpdateDtoToEntity(clienteUpdateDTO);

        // Assert
        assertNotNull(resultado);
        assertEquals("João Santos", resultado.getNome());
        assertEquals("joao.santos@email.com", resultado.getEmail());
        assertEquals("11777777777", resultado.getTelefone());
        assertEquals("Avenida Nova, 456", resultado.getEndereco());
        assertNull(resultado.getCpf()); // CPF não deve ser setado no update
    }

    @Test
    @DisplayName("Deve deletar cliente existente")
    void deletar_DeveRetornarTrue_QuandoClienteExistir() {
        // Arrange
        when(repoCliente.existsById(1L)).thenReturn(true);
        doNothing().when(repoCliente).deleteById(1L);

        // Act
        boolean resultado = serviceCliente.deletar(1L);

        // Assert
        assertTrue(resultado);
        verify(repoCliente).existsById(1L);
        verify(repoCliente).deleteById(1L);
    }

    @Test
    @DisplayName("Deve retornar false ao tentar deletar cliente inexistente")
    void deletar_DeveRetornarFalse_QuandoClienteNaoExistir() {
        // Arrange
        when(repoCliente.existsById(999L)).thenReturn(false);

        // Act
        boolean resultado = serviceCliente.deletar(999L);

        // Assert
        assertFalse(resultado);
        verify(repoCliente).existsById(999L);
        verify(repoCliente, never()).deleteById(anyLong());
    }

    @Test
    @DisplayName("Deve manter dados originais quando campos do update são null")
    void atualizar_DeveManterDadosOriginais_QuandoCamposUpdateSaoNull() {
        // Arrange
        Cliente clienteExistente = new Cliente(1L, "João Silva", "joao@email.com", "12345678900", "11999999999");
        clienteExistente.setEndereco("Endereço Original");
        
        ClienteUpdateDTO updateVazio = new ClienteUpdateDTO(); // Todos campos null
        
        when(repoCliente.findById(1L)).thenReturn(Optional.of(clienteExistente));
        when(repoCliente.save(any(Cliente.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Optional<Cliente> resultado = serviceCliente.atualizar(1L, updateVazio);

        // Assert
        assertTrue(resultado.isPresent());
        Cliente clienteAtualizado = resultado.get();
        
        // Todos os dados devem permanecer os mesmos
        assertEquals("João Silva", clienteAtualizado.getNome());
        assertEquals("joao@email.com", clienteAtualizado.getEmail());
        assertEquals("11999999999", clienteAtualizado.getTelefone());
        assertEquals("Endereço Original", clienteAtualizado.getEndereco());
        assertEquals("12345678900", clienteAtualizado.getCpf());
    }

    @Test
    @DisplayName("Deve verificar injeção de dependências")
    void shouldVerifyDependencies() {
        assertNotNull(serviceCliente);
        assertNotNull(repoCliente);
    }
}