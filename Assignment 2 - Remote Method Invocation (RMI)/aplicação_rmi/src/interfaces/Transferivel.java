package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public class Transferivel extends Remote {
    void transferir(String novoProprietario) throws RemoteException;
}
