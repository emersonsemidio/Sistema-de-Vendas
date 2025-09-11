package com.projeto.sistema.dto;

import java.time.LocalDateTime;

public class MensagemResponseDto {
    private String mensagem;
    private String codigo;
    private LocalDateTime timestamp;
    private Object dados;

    // Construtores

    
    public MensagemResponseDto() {
        this.timestamp = LocalDateTime.now();
    }

    public MensagemResponseDto(String mensagem) {
        this.mensagem = mensagem;
    }

    public MensagemResponseDto(String mensagem, String codigo) {
        this();
        this.mensagem = mensagem;
        this.codigo = codigo;
    }

    public MensagemResponseDto(String mensagem, String codigo, Object dados) {
        this();
        this.mensagem = mensagem;
        this.codigo = codigo;
        this.dados = dados;
    }

    // Getters e Setters
    public String getMensagem() { return mensagem; }
    public void setMensagem(String mensagem) { this.mensagem = mensagem; }
    
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    
    public Object getDados() { return dados; }
    public void setDados(Object dados) { this.dados = dados; }
}