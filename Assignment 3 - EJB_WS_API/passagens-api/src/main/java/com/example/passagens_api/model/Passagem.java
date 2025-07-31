package com.example.passagens_api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

public abstract class Passagem {
    private String codigo;
    private String proprietario;
    private double preco;
    
    @JsonBackReference // 👈 ESTA ANOTAÇÃO É CRUCIAL
    private Voo voo;

    public Passagem(Voo voo, String codigo, String proprietario, double preco) {
        this.voo = voo;
        this.codigo = codigo;
        this.proprietario = proprietario;
        this.preco = preco;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getProprietario() {
        return proprietario;
    }

    public void setProprietario(String proprietario) {
        this.proprietario = proprietario;
    }

    public double getPreco() {
        return preco;
    }

    public Voo getVoo() {
        return voo;
    }
}