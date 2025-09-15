package com.projeto.sistema.repo;

import com.projeto.sistema.Enum.FormaPagamento;
import com.projeto.sistema.Enum.StatusPedido;
import com.projeto.sistema.model.Compra;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RepoCompra extends CrudRepository<Compra, Long> {
     // Buscar todas as vendas de um determinado cliente
    List<Compra> findByClienteId(Long clienteId);

    // Buscar todas as vendas feitas por um determinado mercado
    List<Compra> findByMercadoId(Long mercadoId);

    // Buscar vendas por status (Ex: PENDENTE, CONCLUIDA, CANCELADA)
    List<Compra> findByStatus(StatusPedido status);

    // Buscar vendas por forma de pagamento (Ex: DINHEIRO, CARTAO, PIX)
    List<Compra> findByFormaPagamento(FormaPagamento formaPagamento);

    // Buscar vendas por cliente e status (Ex: todas as vendas pendentes do cliente X)
    List<Compra> findByClienteIdAndStatus(Long clienteId, StatusPedido status);
}
