package test;

import java.io.*;
import java.net.*;
import streams.PassagemOutputStream;
import model.*;

public class ServidorTCPTeste {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(12345);
        System.out.println("Servidor TCP ouvindo na porta 12345...");

        // Dados de exemplo para enviar
        Voo voo = new Voo("V123", "São Paulo", "Nova York");
        Passagem[] passagens = new Passagem[2];
        passagens[0] = new PassagemPrimeiraClasse("PC-001", "João Silva", voo, 8500.0, true, 1000);
        passagens[1] = new PassagemClasseEconomica("EC-205", "Maria Souza", voo, 2200.0, true, 15);

        while (true) {
            try (Socket socket = serverSocket.accept()) {
                System.out.println("Cliente conectado: " + socket.getInetAddress());
                
                // Envia dados para o cliente
                PassagemOutputStream pos = new PassagemOutputStream(
                    passagens, 
                    passagens.length, 
                    socket.getOutputStream()
                );
                pos.enviarPassagens();
                pos.close();
                
                System.out.println("Dados enviados para o cliente.");
            }
        }
    }
}