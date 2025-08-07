package com.projeto.sistema.repo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository; 
import com.projeto.sistema.model.ItemCarrinho;
import java.util.List;

@Repository
public interface RepoItemCarrinho extends JpaRepository<ItemCarrinho, Long> {
    // Busca itens do carrinho de um usuário
    List<ItemCarrinho> findByUsuarioId(Long usuarioId);

    // Remove todos os itens de um usuário (ao finalizar compra)
    void deleteByUsuarioId(Long usuarioId);
}