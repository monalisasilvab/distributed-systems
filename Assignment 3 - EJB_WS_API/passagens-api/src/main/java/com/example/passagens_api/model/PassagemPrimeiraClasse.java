package com.example.passagens_api.model;

public class PassagemPrimeiraClasse extends Passagem {

    public PassagemPrimeiraClasse() {
        super(null, null, null, 0); // necessário para o Jackson
    }
    
    public PassagemPrimeiraClasse(Voo voo, String codigo, String proprietario, double preco) {
        super(voo, codigo, proprietario, preco);
        voo.addPassagem(this);
    }
    
}
