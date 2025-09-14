package com.projeto.sistema.model;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@Table(name = "itens_venda")
public class ItemVenda {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "produto_id")
  private Produto produto;

  @NotBlank(message = "O campo é obrigatorio")
  @Size(min = 1, message = "A quantidade deve ser pelo menos 1")
  private Integer quantidade;

  @NotBlank(message = "O campo é obrigatorio")
  @Size(min = 1, message = "O preço unitário deve ser pelo menos 1")
  private Double precoUnitario;

  // Getters and Setters

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Produto getProduto() {
    return produto;
  }

  public void setProduto(Produto produto) {
    this.produto = produto;
  }


  public Integer getQuantidade() {
    return quantidade;
  }

  public void setQuantidade(Integer quantidade) {
    this.quantidade = quantidade;
  }

  public Double getPrecoUnitario() {
    return precoUnitario;
  }

  public void setPrecoUnitario(Double precoUnitario) {
    this.precoUnitario = precoUnitario;
  }
  
}
