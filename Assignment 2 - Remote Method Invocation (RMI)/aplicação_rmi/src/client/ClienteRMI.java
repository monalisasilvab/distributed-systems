package client;

import java.rmi.registry.LocateRegistry;

import interfaces.ServicoPassagens;
import model.*;
import java.rmi.registry.Registry;

public class ClienteRMI {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);

            ServicoPassagens servico = (ServicoPassagens) registry.lookup("ServicoPassagens");

            Voo voo = new Voo("1234", "São Paulo", "Nova York");

            Passagem p1 = servico.reservarPassagemPrimeiraClasse(voo, "PC-001", "João Silva", 5000.0);
            Passagem p2 = servico.reservarPassagemEconomica(voo, "EC-205", "Maria Souza", 1500.0, 15);

            System.out.println("Reservas realizadas:");
            System.out.println("- " + p1.getCodigo() + ": " + p1.getProprietario()); 
            System.out.println("- " + p2.getCodigo() + ": " + p2.getProprietario());

            servico.transferirPassagem(p1, "Carlos Oliveira");
            System.out.println("\nPassagem transferida: " + p1.getCodigo() + " -> " + p1.getProprietario());

            servico.cancelarReserva(voo, p2);
            System.out.println("Reserva cancelada: " + p2.getCodigo());
        } catch (Exception e) {
            e.printStackTrace();
        } 
   }
}