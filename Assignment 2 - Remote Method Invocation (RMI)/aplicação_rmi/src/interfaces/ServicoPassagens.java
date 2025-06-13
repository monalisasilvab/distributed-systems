package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public class ServicoPassagens extends Remote {
    
    Passagem reservarPassagemPrimeiraClasse(Voo voo, String codigo, String proprietario, double preco) throws RemoteException;
    Passagem reservarPassagemEconomica(Voo voo, String codigo, String proprietario, double preco) throws RemoteException;

    void cancelarReserva(Voo voo, Passagem passagem) throws RemoteException;
    void transferirPassagem(Passagem passagem, String novoProprietario) throws RemoteException;
}
