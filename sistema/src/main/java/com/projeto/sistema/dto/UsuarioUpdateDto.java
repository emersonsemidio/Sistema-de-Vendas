package com.projeto.sistema.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public class UsuarioUpdateDto {

    @Size(min = 3, max = 50, message = "O campo deve ter entre 3 e 50 caracteres")
    private String nome;

    @Email(message = "O campo email deve ser um email válido")
    private String email;
  
    @Size(min = 6, max = 100, message = "O campo deve ter entre 6 e 100 caracteres")
    private String senha;

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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
