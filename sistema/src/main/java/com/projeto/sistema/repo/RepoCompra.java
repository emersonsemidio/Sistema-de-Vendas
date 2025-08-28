package com.projeto.sistema.repo;

import com.projeto.sistema.Enum.FormaPagamento;
import com.projeto.sistema.Enum.StatusPedido;
import com.projeto.sistema.model.Compras;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RepoCompra extends CrudRepository<Compras, Long> {
     // Buscar todas as vendas de um determinado cliente
    List<Compras> findByClienteId(Long clienteId);

    // Buscar todas as vendas feitas por um determinado usuário (vendedor)
    List<Compras> findByUsuarioId(Long usuarioId);

    // Buscar vendas por status (Ex: PENDENTE, CONCLUIDA, CANCELADA)
    List<Compras> findByStatus(StatusPedido status);

    // Buscar vendas por forma de pagamento (Ex: DINHEIRO, CARTAO, PIX)
    List<Compras> findByFormaPagamento(FormaPagamento formaPagamento);

    // Buscar vendas por cliente e status (Ex: todas as vendas pendentes do cliente X)
    List<Compras> findByClienteIdAndStatus(Long clienteId, StatusPedido status);
}
