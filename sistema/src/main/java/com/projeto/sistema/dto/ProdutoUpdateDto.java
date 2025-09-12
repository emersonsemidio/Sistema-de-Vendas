package com.projeto.sistema.dto;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Positive;

public class ProdutoUpdateDto {


  @Size(min = 3, max = 50, message = "O nome do produto deve ter entre 3 e 50 caracteres")
  private String nome;

  @Size(min = 10, max = 200, message = "A descrição deve ter entre 10 e 200 caracteres")
  private String descricao;

  @Positive(message = "O campo deve ser um número positivo")
  private Double preco;

  @Positive(message = "O campo deve ser um número positivo")
  private Integer quantidade;

  @Size(min = 5, max = 100, message = "A URL da imagem deve ter entre 5 e 100 caracteres")
  private String imagemUrl;

  // Getters and Setters

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

}
