package model;

import java.rmi.RemoteException;

import interfaces.Transferivel;

public class PassagemPrimeiraClasse extends Passagem implements Transferivel{
    private boolean acessoSalaVIP;
    private int milhasBonus;
    
    public PassagemPrimeiraClasse(String codigo, String proprietario, Voo voo, double preco, boolean acessoSalaVIP, int milhasBonus) {
        super(codigo, proprietario, voo, preco);
        this.acessoSalaVIP = acessoSalaVIP;
        this.milhasBonus = milhasBonus;
    }

    public boolean hasAcessoSalaVIP() {
        return acessoSalaVIP;
    }

    public int getMilhasBonus() {
        return milhasBonus;
    }

    @Override
    public void transferir(String novoProprietario) throws RemoteException {
        setProprietario(novoProprietario); // Atualiza o proprietário via setter
    }
}
