package model;

import java.io.Serializable;
import interfaces.Transferivel;

public abstract class Passagem implements Transferivel, Serializable { 
    private String codigo;
    private String proprietario;
    private Voo voo;
    private double preco;

    public Passagem(String codigo, String proprietario, Voo voo, double preco) {
        this.codigo = codigo;
        this.proprietario = proprietario;
        this.voo = voo;
        this.preco = preco;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getProprietario() {
        return proprietario;
    }

    public Voo getVoo() {
        return voo;
    }

    public double getPreco() {
        return preco;
    }

    public void setProprietario(String proprietario) {
        this.proprietario = proprietario;
    }
}
