package model;

public abstract class PassagemClasseEconomica extends Passagem {
    private boolean bagagemGratuita;
    private int numeroAssento;

    public PassagemClasseEconomica(String codigo, String proprietario, Voo voo, double preco, boolean bagagemGratuita, int numeroAssento) {
        super(codigo, proprietario, voo, preco);
        this.bagagemGratuita = bagagemGratuita;
        this.numeroAssento = numeroAssento;
    }

    public boolean hasBagagemGratuita() {
        return bagagemGratuita;
    }

    public int getNumeroAssento() {
        return numeroAssento;
    }
}
