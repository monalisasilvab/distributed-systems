package test;

import java.io.*;
import java.net.*;

public class ServidorTCPTeste {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(12345);
        System.out.println("Servidor TCP ouvindo na porta 12345...");

        while (true) {
            try (Socket socket = serverSocket.accept();
                 DataInputStream dis = new DataInputStream(socket.getInputStream())) {
                
                System.out.println("\nConexão recebida de: " + socket.getInetAddress());
                
                int numPassagens = dis.readInt();
                System.out.println("Número de passagens: " + numPassagens);
                
                for (int i = 0; i < numPassagens; i++) {
                    // Ler tamanhos dos atributos
                    int tamCodigo = dis.readInt();
                    int tamProprietario = dis.readInt();
                    int tamPreco = dis.readInt();
                    
                    // Ler dados dos atributos
                    byte[] codigoBytes = new byte[tamCodigo];
                    dis.readFully(codigoBytes);
                    
                    byte[] proprietarioBytes = new byte[tamProprietario];
                    dis.readFully(proprietarioBytes);
                    
                    byte[] precoBytes = new byte[tamPreco];
                    dis.readFully(precoBytes);
                    
                    // Converter para string
                    String codigo = new String(codigoBytes, "UTF-8");
                    String proprietario = new String(proprietarioBytes, "UTF-8");
                    String precoStr = new String(precoBytes, "UTF-8");
                    
                    System.out.println("\nPassagem " + (i + 1) + ":");
                    System.out.println("Código: " + codigo);
                    System.out.println("Proprietário: " + proprietario);
                    System.out.println("Preço: " + precoStr);
                }
            }
        }
    }
}