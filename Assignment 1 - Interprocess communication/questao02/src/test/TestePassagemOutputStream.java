package test;

import model.*;
import streams.PassagemOutputStream;
import java.io.*;
import java.net.Socket;

public class TestePassagemOutputStream {
    public static void main(String[] args) throws IOException {
        // Criar voo e passagens de teste
        Voo voo = new Voo("V123", "São Paulo", "Nova York");
        Passagem[] passagens = new Passagem[3];
        passagens[0] = new PassagemPrimeiraClasse("PC-001", "João Silva", voo, 8500.0, true, 1000);
        passagens[1] = new PassagemClasseEconomica("EC-205", "Maria Souza", voo, 2200.0, true, 15);
        passagens[2] = new PassagemPrimeiraClasse("PC-002", "Carlos Oliveira", voo, 9200.0, false, 500);

        // 1. Teste com saída padrão (System.out)
        testarSaidaPadrao(passagens);
        
        // 2. Teste com arquivo
        testarArquivo(passagens, "passagens.dat");
        
        // 3. Teste com servidor TCP
        testarServidorTCP(passagens, "localhost", 12345);
    }

    private static void testarSaidaPadrao(Passagem[] passagens) throws IOException {
        System.out.println("\n--- Teste Saída Padrão ---");
        PassagemOutputStream pos = new PassagemOutputStream(passagens, passagens.length, System.out);
        pos.enviarPassagens();
        pos.close();
    }

    private static void testarArquivo(Passagem[] passagens, String arquivo) throws IOException {
        System.out.println("\n--- Teste Arquivo ---");
        try (FileOutputStream fos = new FileOutputStream(arquivo)) {
            PassagemOutputStream pos = new PassagemOutputStream(passagens, passagens.length, fos);
            pos.enviarPassagens();
            pos.close();
        }
        System.out.println("Dados escritos em: " + arquivo);
        lerArquivo(arquivo);
    }

    private static void testarServidorTCP(Passagem[] passagens, String host, int porta) throws IOException {
        System.out.println("\n--- Teste Servidor TCP ---");
        try (Socket socket = new Socket(host, porta)) {
            PassagemOutputStream pos = new PassagemOutputStream(
                passagens, 
                passagens.length, 
                socket.getOutputStream()
            );
            pos.enviarPassagens();
            pos.close();
            System.out.println("Dados enviados para servidor: " + host + ":" + porta);
        }
    }

    private static void lerArquivo(String arquivo) throws IOException {
        System.out.println("\nConteúdo do arquivo (hex):");
        try (FileInputStream fis = new FileInputStream(arquivo)) {
            int byteLido;
            while ((byteLido = fis.read()) != -1) {
                System.out.printf("%02X ", byteLido);
            }
            System.out.println("\n");
        }
    }
}