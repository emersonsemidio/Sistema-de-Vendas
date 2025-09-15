package com.projeto.sistema.dto;

import java.util.List;

import com.projeto.sistema.Enum.FormaPagamento;
import com.projeto.sistema.Enum.StatusPedido;

import jakarta.validation.constraints.Min;
import java.util.ArrayList;
import jakarta.validation.constraints.NotNull;

public class CompraRegisterDto {
  private Long clienteId;
  private Double total;

  @NotNull(message = "O campo Usuário é obrigatorio")
  @Min(value = 1, message = "O ID do usuário deve ser pelo menos 1")
  private Long usuarioId;

  @NotNull(message = "O campo forma de pagamento é obrigatorio")
  private FormaPagamento formaPagamento;
  private StatusPedido status;

  @NotNull(message = "O campo itens é obrigatorio")
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
