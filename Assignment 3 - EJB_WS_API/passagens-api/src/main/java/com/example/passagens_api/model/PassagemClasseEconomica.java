package com.example.passagens_api.model;

public class PassagemClasseEconomica extends Passagem {
    public PassagemClasseEconomica() {
        super(null, null, null, 0); // necessário para o Jackson
    }
    
    private int assento;

    public PassagemClasseEconomica(Voo voo, String codigo, String proprietario, double preco, int assento) {
        super(voo, codigo, proprietario, preco);
        this.assento = assento;
        voo.addPassagem(this);
    }
    
    public int getAssento() {
        return assento;
    }

}
