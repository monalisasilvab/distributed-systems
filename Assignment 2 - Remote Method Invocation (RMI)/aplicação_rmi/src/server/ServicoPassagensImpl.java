package server;

import interfaces.*;
import model.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServicoPassagensImpl extends UnicastRemoteObject implements ServicoPassagens {
    protected ServicoPassagensImpl() throws RemoteException {
        super();    
    }

    @Override
    public Passagem reservarPassagemPrimeiraClasse(Voo voo, String codigo, 
                                                  String proprietario, double preco) throws RemoteException {
        Passagem passagem = new PassagemPrimeiraClasse(codigo, proprietario, voo, preco, true, 1000);
        voo.adicionarPassagem(passagem);
        return passagem;
    }

    @Override
    public Passagem reservarPassagemEconomica(Voo voo, String codigo, 
                                             String proprietario, double preco, int assento) throws RemoteException {
        Passagem passagem = new PassagemClasseEconomica(codigo, proprietario, voo, preco, true, assento);
        voo.adicionarPassagem(passagem);
        return passagem;
    }

    @Override
    public void cancelarReserva(Voo voo, Passagem passagem) throws RemoteException {
        voo.removerPassagem(passagem);
    }

    @Override
    public void transferirPassagem(Passagem passagem, String novoProprietario) throws RemoteException {
        passagem.setProprietario(novoProprietario);
    }
}