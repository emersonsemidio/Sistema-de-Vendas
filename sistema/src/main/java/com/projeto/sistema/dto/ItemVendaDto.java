package com.projeto.sistema.dto;

public class ItemVendaDto {

  private Long produtoId;
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
