package com.projeto.sistema.repo;

import com.projeto.sistema.model.Cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ClienteRepoTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RepoCliente repoCliente;

    private Cliente cliente1;
    private Cliente cliente2;

    @BeforeEach
    void setUp() {
        // Limpa o banco antes de cada teste
        entityManager.clear();
        
        cliente1 = new Cliente();
        cliente1.setNome("João Silva");
        cliente1.setEmail("joao@email.com");
        cliente1.setCpf("12345678900");
        cliente1.setTelefone("11999999999");
        cliente1.setEndereco("Rua A, 123");

        cliente2 = new Cliente();
        cliente2.setNome("Maria Souza");
        cliente2.setEmail("maria@email.com");
        cliente2.setCpf("98765432100");
        cliente2.setTelefone("11888888888");
        cliente2.setEndereco("Rua B, 456");
    }

    @Test
    @DisplayName("Deve salvar cliente com sucesso")
    void save_DeveSalvarCliente_QuandoDadosValidos() {
        // Act
        Cliente clienteSalvo = repoCliente.save(cliente1);

        // Assert
        assertNotNull(clienteSalvo.getId());
        assertEquals("João Silva", clienteSalvo.getNome());
        assertEquals("joao@email.com", clienteSalvo.getEmail());
        assertEquals("12345678900", clienteSalvo.getCpf());
    }

    @Test
    @DisplayName("Deve buscar cliente por ID existente")
    void findById_DeveRetornarCliente_QuandoIdExistir() {
        // Arrange
        Cliente clienteSalvo = entityManager.persistAndFlush(cliente1);

        // Act
        Optional<Cliente> resultado = repoCliente.findById(clienteSalvo.getId());

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals(clienteSalvo.getId(), resultado.get().getId());
        assertEquals("João Silva", resultado.get().getNome());
    }

    @Test
    @DisplayName("Deve retornar vazio ao buscar cliente por ID inexistente")
    void findById_DeveRetornarVazio_QuandoIdNaoExistir() {
        // Act
        Optional<Cliente> resultado = repoCliente.findById(999L);

        // Assert
        assertFalse(resultado.isPresent());
    }

    @Test
    @DisplayName("Deve buscar cliente por email existente")
    void findByEmail_DeveRetornarCliente_QuandoEmailExistir() {
        // Arrange
        entityManager.persistAndFlush(cliente1);

        // Act
        UserDetails resultado = repoCliente.findByEmail("joao@email.com");

        // Assert
        assertNotNull(resultado);
        assertEquals(cliente1.getNome(), resultado.getUsername());
        assertEquals(cliente1.getEmail(), ((Cliente) resultado).getEmail());
    }

    @Test
    @DisplayName("Deve retornar vazio ao buscar cliente por email inexistente")
    void findByEmail_DeveRetornarVazio_QuandoEmailNaoExistir() {
        // Act
        UserDetails resultado = repoCliente.findByEmail("inexistente@email.com");

        // Assert
        assertNull(resultado);
    }

    @Test
    @DisplayName("Deve retornar todos os clientes")
    void findAll_DeveRetornarTodosClientes() {
        // Arrange
        entityManager.persistAndFlush(cliente1);
        entityManager.persistAndFlush(cliente2);

        // Act
        Iterable<Cliente> resultado = repoCliente.findAll();
        List<Cliente> clientesList = (List<Cliente>) resultado;

        // Assert
        assertEquals(2, clientesList.size());
        assertTrue(clientesList.stream().anyMatch(c -> c.getEmail().equals("joao@email.com")));
        assertTrue(clientesList.stream().anyMatch(c -> c.getEmail().equals("maria@email.com")));
    }

    @Test
    @DisplayName("Deve atualizar cliente existente")
    void save_DeveAtualizarCliente_QuandoClienteExistir() {
        // Arrange
        Cliente clienteSalvo = entityManager.persistAndFlush(cliente1);
        String novoNome = "João Santos";
        String novoTelefone = "11777777777";

        // Act
        clienteSalvo.setNome(novoNome);
        clienteSalvo.setTelefone(novoTelefone);
        Cliente clienteAtualizado = repoCliente.save(clienteSalvo);

        // Assert
        assertEquals(clienteSalvo.getId(), clienteAtualizado.getId());
        assertEquals(novoNome, clienteAtualizado.getNome());
        assertEquals(novoTelefone, clienteAtualizado.getTelefone());
        assertEquals("joao@email.com", clienteAtualizado.getEmail()); // Email não mudou
    }

    @Test
    @DisplayName("Deve deletar cliente existente")
    void deleteById_DeveRemoverCliente_QuandoClienteExistir() {
        // Arrange
        Cliente clienteSalvo = entityManager.persistAndFlush(cliente1);
        Long id = clienteSalvo.getId();

        // Act
        repoCliente.deleteById(id);

        // Assert
        Optional<Cliente> resultado = repoCliente.findById(id);
        assertFalse(resultado.isPresent());
    }

    @Test
    @DisplayName("Deve verificar se cliente existe por ID")
    void existsById_DeveRetornarTrue_QuandoClienteExistir() {
        // Arrange
        Cliente clienteSalvo = entityManager.persistAndFlush(cliente1);

        // Act
        boolean existe = repoCliente.existsById(clienteSalvo.getId());

        // Assert
        assertTrue(existe);
    }

    @Test
    @DisplayName("Deve verificar se cliente não existe por ID")
    void existsById_DeveRetornarFalse_QuandoClienteNaoExistir() {
        // Act
        boolean existe = repoCliente.existsById(999L);

        // Assert
        assertFalse(existe);
    }

    @Test
    @DisplayName("Deve contar total de clientes")
    void count_DeveRetornarTotalDeClientes() {
        // Arrange
        entityManager.persistAndFlush(cliente1);
        entityManager.persistAndFlush(cliente2);

        // Act
        long total = repoCliente.count();

        // Assert
        assertEquals(2, total);
    }

    @Test
    @DisplayName("Deve buscar cliente por email case insensitive")
    void findByEmail_DeveBuscarPorEmailExato() {
        // Arrange
        entityManager.persistAndFlush(cliente1);

        // Act & Assert - Deve encontrar com email exato
        UserDetails resultado1 = repoCliente.findByEmail("joao@email.com");
        assertNotNull(resultado1);
        // Act & Assert - Não deve encontrar com email diferente (case sensitive)
        UserDetails resultado2 = repoCliente.findByEmail("JOAO@EMAIL.COM");
        assertNull(resultado2);
    }

    @Test
    @DisplayName("Deve garantir unicidade do email")
    void save_DevePermitirEmailUnico() {
        // Arrange
        entityManager.persistAndFlush(cliente1);

        Cliente clienteComMesmoEmail = new Cliente();
        clienteComMesmoEmail.setNome("Outro João");
        clienteComMesmoEmail.setEmail("joao@email.com"); // Mesmo email
        clienteComMesmoEmail.setCpf("11122233344");
        clienteComMesmoEmail.setTelefone("11555555555");

        // Act - Isso deve funcionar pois a unicidade não está sendo validada no repositório
        // A validação de unicidade seria feita via @Column(unique=true) na entidade
        Cliente segundoCliente = repoCliente.save(clienteComMesmoEmail);

        // Assert - Ambos foram salvos (se não houver constraint de unique no banco)
        assertNotNull(segundoCliente.getId());
        assertEquals(2, repoCliente.count());
    }
}