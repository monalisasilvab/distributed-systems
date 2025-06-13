package model;

public abstract class PassagemPrimeiraClasse extends Passagem{
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
}
