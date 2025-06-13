package server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import interfaces.ServicoPassagens;

public class ServidorRMI {
    public static void main(String[] args) {
        try {
            ServicoPassagens servico = new ServicoPassagensImpl();

            Registry registry = LocateRegistry.createRegistry(1099);

            registry.rebind("ServicoPassagens", servico);

            System.out.println("Servidor RMI iniciado e aguardando conexões...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
