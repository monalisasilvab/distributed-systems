package test;

import streams.*;
import model.Passagem;
import java.io.*;
import java.net.Socket;
import java.util.List;

public class TestePassagemInputStream {
    public static void main(String[] args) throws IOException {
        // a) Teste com entrada padrão (System.in)
        testarEntradaPadrao();
        
        // b) Teste com arquivo
        testarArquivo("passagens.dat");
        
        // c) Teste com servidor TCP
        testarServidorTCP("localhost", 12345);
    }

    private static void testarEntradaPadrao() throws IOException {
        System.out.println("\n--- Teste Entrada Padrão ---");
        System.out.println("Digite os dados de passagens (binário) e pressione Ctrl+D para finalizar:");
        
        PassagemInputStream pis = new PassagemInputStream(System.in);
        List<Passagem> passagens = pis.lerPassagens();
        pis.close();
        
        exibirPassagens(passagens);
    }

    private static void testarArquivo(String arquivo) throws IOException {
        System.out.println("\n--- Teste Arquivo ---");
        try (FileInputStream fis = new FileInputStream(arquivo)) {
            PassagemInputStream pis = new PassagemInputStream(fis);
            List<Passagem> passagens = pis.lerPassagens();
            pis.close();
            
            System.out.println("Passagens lidas do arquivo:");
            exibirPassagens(passagens);
        }
    }

    private static void testarServidorTCP(String host, int porta) throws IOException {
        System.out.println("\n--- Teste Servidor TCP ---");
        try (Socket socket = new Socket(host, porta)) {
            PassagemInputStream pis = new PassagemInputStream(socket.getInputStream());
            List<Passagem> passagens = pis.lerPassagens();
            pis.close();
            
            System.out.println("Passagens recebidas do servidor:");
            exibirPassagens(passagens);
        }
    }

    private static void exibirPassagens(List<Passagem> passagens) {
        for (Passagem p : passagens) {
            System.out.println("• " + p.getCodigo() + 
                " - " + p.getProprietario() +
                " - R$" + p.getPreco());
        }
    }
}