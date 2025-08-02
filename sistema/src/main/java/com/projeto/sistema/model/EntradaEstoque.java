package com.projeto.sistema.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "entradas_estoque")
public class EntradaEstoque {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "O campo é obrigatorio")
  @Positive(message = "O campo deve ser um número positivo")
  private Long produtoId;

  @NotBlank(message = "O campo é obrigatorio")
  @Positive(message = "O campo deve ser um número positivo")
  private Integer quantidade;

  @NotBlank(message = "O campo é obrigatorio")
  @Size(min = 10, max = 10, message = "A data deve estar no formato YYYY-MM-DD")
  private LocalDate dataEntrada;
  private Long fornecedorId;

  @NotBlank(message = "O campo é obrigatorio")
  @Positive(message = "O campo deve ser um número positivo")
  private Double custoUnitario;

  // Getters and Setters

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getProdutoId() {
    return produtoId;
  }

  public void setProdutoId(Long produtoId) {
    this.produtoId = produtoId;
  }

  public Integer getQuantidade() {
    return quantidade;
  }

  public void setQuantidade(Integer quantidade) {
    this.quantidade = quantidade;
  }
  public LocalDate getDataEntrada() {
    return dataEntrada;
  }

  public void setDataEntrada(LocalDate dataEntrada) {
    this.dataEntrada = dataEntrada;
  }

  public Long getFornecedorId() {
    return fornecedorId;
  }

  public void setFornecedorId(Long fornecedorId) {
    this.fornecedorId = fornecedorId;
  }

  public Double getCustoUnitario() {
    return custoUnitario;
  } 

  public void setCustoUnitario(Double custoUnitario) {
    this.custoUnitario = custoUnitario;
  }

}
