package com.projeto.sistema.repo;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.projeto.sistema.model.Usuario;


@Repository
public interface RepoUsuario extends CrudRepository<Usuario, Long> {

}
