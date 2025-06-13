package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import model.*;

public interface ServicoPassagens extends Remote {
    // Métodos remotos
    Passagem reservarPassagemPrimeiraClasse(Voo voo, String codigo, 
                                           String proprietario, double preco) throws RemoteException;
    
    Passagem reservarPassagemEconomica(Voo voo, String codigo, 
                                      String proprietario, double preco, int assento) throws RemoteException;
    
    void cancelarReserva(Voo voo, Passagem passagem) throws RemoteException;
    
    void transferirPassagem(Passagem passagem, String novoProprietario) throws RemoteException;
}