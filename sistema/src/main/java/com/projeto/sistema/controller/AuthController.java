package com.projeto.sistema.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.sistema.dto.AuthenticationDto;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth")
public class AuthController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @PostMapping("/login")
  public ResponseEntity login(@RequestBody @Valid AuthenticationDto authenticationDto) {
    var usernamePassword = new UsernamePasswordAuthenticationToken(authenticationDto.email(), authenticationDto.senha());
    var auth = this.authenticationManager.authenticate(usernamePassword);

    return ResponseEntity.ok().build();
  }
  
  
}
