package com.projeto.sistema.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class ItemVendaDto {

  @NotNull(message = "O campo produtoId é obrigatorio")
  @Min(value = 1, message = "O ID do produto deve ser pelo menos 1")
  private Long produtoId;

  @NotNull(message = "O campo quantidade é obrigatorio")
  @Min(value = 1, message = "A quantidade deve ser pelo menos 1")
  private Integer quantidade;


  // Getters and Setters

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
  
}
