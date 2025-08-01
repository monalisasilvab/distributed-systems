package com.example.passagens_api.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.ArrayList;
import java.util.List;

public class Voo {
    private String numeroVoo;
    private String origem;
    private String destino;   
    
    @JsonManagedReference // 👈 ESTA ANOTAÇÃO É CRUCIAL
    private List<Passagem> passagens = new ArrayList<>();

    public Voo() {
    }

    public Voo(String numeroVoo, String origem, String destino) {
        this.numeroVoo = numeroVoo;
        this.origem = origem;
        this.destino = destino;
    }

    // Getters e setters permanecem iguais
    public String getNumeroVoo() {
        return numeroVoo;
    }

    public void setNumeroVoo(String numeroVoo) {
        this.numeroVoo = numeroVoo;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public List<Passagem> getPassagens() {
        return new ArrayList<>(passagens);
    }

    public void addPassagem(Passagem p) {
        passagens.add(p);
    }

    public void removePassagem(Passagem p) {
        passagens.remove(p);
    }

    public void setPassagens(List<Passagem> passagens) {
    this.passagens = passagens;
}
}