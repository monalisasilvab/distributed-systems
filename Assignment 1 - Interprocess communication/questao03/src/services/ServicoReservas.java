package services;

import model.Passagem;
import model.PassagemPrimeiraClasse;
import model.PassagemClasseEconomica;
import model.Voo;

public class ServicoReservas {
    public Passagem reservarPassagemPrimeiraClasse(
            Voo voo, String codigo, String proprietario, double preco) {
        
        Passagem passagem = new PassagemPrimeiraClasse(
            codigo, proprietario, voo, preco, true, 1000
        );
        voo.adicionarPassagem(passagem);
        return passagem;
    }

    public Passagem reservarPassagemEconomica(
            Voo voo, String codigo, String proprietario, double preco, int assento) {
        
        Passagem passagem = new PassagemClasseEconomica(
            codigo, proprietario, voo, preco, true, assento
        );
        voo.adicionarPassagem(passagem);
        return passagem;
    }

    public void cancelarReserva(Voo voo, Passagem passagem) {
        voo.removerPassagem(passagem);
        System.out.println("Reserva cancelada: " + passagem.getCodigo());
    }
}