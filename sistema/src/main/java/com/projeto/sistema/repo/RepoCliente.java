package com.projeto.sistema.repo;


import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.projeto.sistema.model.Cliente;

@Repository
public interface RepoCliente extends CrudRepository<Cliente, Long> {

  UserDetails findByEmail(String email);


}
