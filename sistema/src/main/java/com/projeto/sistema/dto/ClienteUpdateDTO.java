package com.projeto.sistema.dto;

import jakarta.validation.constraints.Size;

public class ClienteUpdateDTO {
  @Size(min = 3, max = 50)
  private String nome;

  @Size(min = 3, max = 50)
  private String email;

  @Size(min = 3, max = 50)
  private String telefone;

  @Size(min = 3, max = 100)
  private String endereco;

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

}
