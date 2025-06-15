package model;

import java.rmi.RemoteException;

import interfaces.Transferivel;

public class PassagemClasseEconomica extends Passagem implements Transferivel{
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

    @Override
    public void transferir(String novoProprietario) throws RemoteException {
        setProprietario(novoProprietario); // Atualiza o proprietário via setter
    }


}
