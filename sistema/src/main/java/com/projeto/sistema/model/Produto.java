package com.projeto.sistema.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "produto")
public class Produto {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "O campo é obrigatorio")
  @Size(min = 3, max = 50, message = "O nome do produto deve ter entre 3 e 50 caracteres")
  private String nome;

  @NotBlank(message = "O campo é obrigatorio")
  @Size(min = 10, max = 200, message = "A descrição deve ter entre 10 e 200 caracteres")
  private String descricao;

  @NotBlank(message = "O campo é obrigatorio")
  @Positive(message = "O campo deve ser um número positivo")
  private Double preco;

  @NotBlank(message = "O campo é obrigatorio")
  @Positive(message = "O campo deve ser um número positivo")
  private Integer quantidade;

  @NotBlank(message = "O campo é obrigatorio")
  @Size(min = 5, max = 100, message = "A URL da imagem deve ter entre 5 e 100 caracteres")
  private String imagemUrl;

  
  private String usuarioId;

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

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  public Double getPreco() {
    return preco;
  }

  public void setPreco(Double preco) {
    this.preco = preco;
  }

  public Integer getQuantidade() {
    return quantidade;
  }

  public void setQuantidade(Integer quantidade) {
    this.quantidade = quantidade;
  }

  public String getImagemUrl() {
    return imagemUrl;
  }

  public void setImagemUrl(String imagemUrl) {
    this.imagemUrl = imagemUrl;
  }

  public String getUsuarioId() {
    return usuarioId;
  }

  public void setUsuarioId(String usuarioId) {
    this.usuarioId = usuarioId;
  }

}
