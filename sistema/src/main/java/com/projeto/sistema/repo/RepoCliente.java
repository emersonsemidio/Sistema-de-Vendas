package com.projeto.sistema.repo;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.projeto.sistema.model.Cliente;

@Repository
public interface RepoCliente extends CrudRepository<Cliente, Long> {

}
