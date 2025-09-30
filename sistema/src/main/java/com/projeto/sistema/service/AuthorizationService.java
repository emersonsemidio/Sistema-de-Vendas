package com.projeto.sistema.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.projeto.sistema.repo.RepoCliente;

@Service
public class AuthorizationService implements UserDetailsService {

  @Autowired
  RepoCliente repoCliente;


  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return repoCliente.findByEmail(username);
  }
  
}
