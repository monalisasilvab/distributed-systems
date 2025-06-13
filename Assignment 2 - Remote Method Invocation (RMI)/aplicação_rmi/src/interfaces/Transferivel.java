package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Transferivel extends Remote {
    void transferir(String novoProprietario) throws RemoteException;
}
