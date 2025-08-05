package com.projeto.sistema.repo;

import com.projeto.sistema.Enum.FormaPagamento;
import com.projeto.sistema.Enum.StatusPedido;
import com.projeto.sistema.model.Venda;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RepoVenda extends CrudRepository<Venda, Long> {
     // Buscar todas as vendas de um determinado cliente
    List<Venda> findByClienteId(Long clienteId);

    // Buscar todas as vendas feitas por um determinado usuário (vendedor)
    List<Venda> findByUsuarioId(Long usuarioId);

    // Buscar vendas por status (Ex: PENDENTE, CONCLUIDA, CANCELADA)
    List<Venda> findByStatus(StatusPedido status);

    // Buscar vendas por forma de pagamento (Ex: DINHEIRO, CARTAO, PIX)
    List<Venda> findByFormaPagamento(FormaPagamento formaPagamento);

    // Buscar vendas por cliente e status (Ex: todas as vendas pendentes do cliente X)
    List<Venda> findByClienteIdAndStatus(Long clienteId, StatusPedido status);
}
