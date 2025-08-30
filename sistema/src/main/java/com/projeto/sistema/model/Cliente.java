package com.projeto.sistema.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "cliente")
public class Cliente {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Size(min = 3, max = 50, message = "O campo deve ter entre 3 e 50 caracteres")
  private String nome;

  @Size(min = 3, max = 50, message = "O campo deve ter entre 3 e 50 caracteres")
  private String email;

  @Size(min = 3, max = 50, message = "O campo deve ter entre 3 e 50 caracteres")
  private String telefone;

  @Size(min = 3, max = 100, message = "O campo deve ter entre 3 e 100 caracteres")
  private String endereco;

  @Size(min = 11, max = 14, message = "O campo deve ter entre 11 e 14 caracteres")
  private String cpf;

  public Cliente() {}


  // Getters and Setters

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getTelefone() {
    return telefone;
  }

  public void setTelefone(String telefone) {
    this.telefone = telefone;
  }

  public String getEndereco() {
    return endereco;
  }

  public void setEndereco(String endereco) {
    this.endereco = endereco;
  }

  public String getCpf() {
    return cpf;
  }

  public void setCpf(String cpf) {
    this.cpf = cpf;
  }

  public String toString() {
    return "Cliente{" +
            "id=" + id +
            ", nome='" + nome + '\'' +
            ", email='" + email + '\'' +
            ", telefone='" + telefone + '\'' +
            ", endereco='" + endereco + '\'' +
            ", cpf='" + cpf + '\'' +
            '}';
  }

}
