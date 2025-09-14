package com.projeto.sistema.dto;

import java.util.List;

import com.projeto.sistema.Enum.FormaPagamento;
import com.projeto.sistema.Enum.StatusPedido;
import java.util.ArrayList;
 

public class CompraRegisterDto {
  private Long clienteId;
  private Double total;
  private Long usuarioId;
  private FormaPagamento formaPagamento;
  private StatusPedido status;
  private List <ItemVendaDto> itens = new ArrayList<>();
  
  // Getters and Setters

  public Long getClienteId() {
    return clienteId;
  }

  public void setClienteId(Long clienteId) {
    this.clienteId = clienteId;
  }

  public Long getUsuarioId() {
    return usuarioId;
  }

  public void setUsuarioId(Long usuarioId) {
    this.usuarioId = usuarioId;
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

  public List<ItemVendaDto> getItens() {
    return itens;
  }

  public void setItens(List<ItemVendaDto> itens) {
    this.itens = itens;
  }

}
