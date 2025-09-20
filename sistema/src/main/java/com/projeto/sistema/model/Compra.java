package com.projeto.sistema.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.projeto.sistema.Enum.FormaPagamento;
import com.projeto.sistema.Enum.StatusPedido;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;

@Entity
@Table(name = "compras")
public class Compra {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "cliente_id")
  private Cliente cliente;

  @ManyToOne
  @JoinColumn(name = "mercado_id")
  private Mercado mercado;

  private Double total;

  @Enumerated(EnumType.STRING)
  private FormaPagamento formaPagamento;

  @Enumerated(EnumType.STRING)
  private StatusPedido status;
  
  @OneToMany(mappedBy = "compra", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnore
  private List<ItemVenda> itens = new ArrayList<>();

  public void adicionarItem(ItemVenda item) {
      itens.add(item);
      item.setCompra(this);
  }

  // Getters and Setters

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Cliente getCliente() {
    return cliente;
  }

  public void setCliente(Cliente cliente) {
    this.cliente = cliente;
  }

  public Double getTotal() {
    return total;
  }

  public void setTotal(Double total) {
    this.total = total;
  }

  public FormaPagamento getFormaPagamento() {
    return formaPagamento;
  }

  public void setFormaPagamento(FormaPagamento formaPagamento) {
    this.formaPagamento = formaPagamento;
  }

  public StatusPedido getStatus() {
    return status;
  }

  public void setStatus(StatusPedido status) {
    this.status = status;
  }


  public Mercado getMercado() {
    return mercado;
  }

  public void setMercado(Mercado mercado) {
    this.mercado = mercado;
  }

  public List<ItemVenda> getItens() {
    return itens;
  }

  public void setItens(List<ItemVenda> itens) {
    this.itens = itens;
  }

}
