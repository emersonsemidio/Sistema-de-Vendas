package com.projeto.sistema.repo;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.projeto.sistema.model.Produto;


@Repository
public interface RepoProduto extends CrudRepository<Produto, Long> {

}
