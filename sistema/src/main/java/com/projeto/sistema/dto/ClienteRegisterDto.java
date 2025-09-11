package com.projeto.sistema.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ClienteRegisterDto {

  @NotNull
  @NotBlank(message = "O campo nome é obrigatório")
  @Size(min = 3, max = 50, message = "O campo deve ter entre 3 e 50 caracteres")
  private String nome;

  @NotNull
  @NotBlank(message = "O campo email é obrigatório")
  @Size(min = 3, max = 50, message = "O campo deve ter entre 3 e 50 caracteres")
  private String email;

  @NotNull
  @NotBlank(message = "O campo telefone é obrigatório")
  @Size(min = 3, max = 50, message = "O campo deve ter entre 3 e 50 caracteres")
  private String telefone;

  @NotNull
  @NotBlank(message = "O campo endereco é obrigatório")
  @Size(min = 3, max = 100, message = "O campo deve ter entre 3 e 100 caracteres")
  private String endereco;

  @NotNull
  @NotBlank(message = "O campo CPF é obrigatório")
  @Size(min = 11, max = 14, message = "O campo deve ter entre 11 e 14 caracteres")
  private String cpf;


  // Getters and Setters


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

}
