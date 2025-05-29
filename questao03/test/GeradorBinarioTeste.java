package test;

import java.io.*;
import model.*;

public class GeradorBinarioTeste {
    public static void main(String[] args) throws IOException {
        Voo voo = new Voo("V123", "São Paulo", "Nova York");
        Passagem[] passagens = new Passagem[2];
        passagens[0] = new PassagemPrimeiraClasse("PC-001", "João Silva", voo, 8500.0, true, 1000);
        passagens[1] = new PassagemClasseEconomica("EC-205", "Maria Souza", voo, 2200.0, true, 15);
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        
        // Escreve número de passagens
        dos.writeInt(2);
        
        // Passagem 1
        byte[] codigo1 = "PC-001".getBytes("UTF-8");
        byte[] prop1 = "João Silva".getBytes("UTF-8");
        byte[] preco1 = "8500.0".getBytes("UTF-8");
        
        dos.writeInt(codigo1.length);
        dos.writeInt(prop1.length);
        dos.writeInt(preco1.length);
        dos.write(codigo1);
        dos.write(prop1);
        dos.write(preco1);
        
        // Passagem 2
        byte[] codigo2 = "EC-205".getBytes("UTF-8");
        byte[] prop2 = "Maria Souza".getBytes("UTF-8");
        byte[] preco2 = "2200.0".getBytes("UTF-8");
        
        dos.writeInt(codigo2.length);
        dos.writeInt(prop2.length);
        dos.writeInt(preco2.length);
        dos.write(codigo2);
        dos.write(prop2);
        dos.write(preco2);
        
        System.out.write(baos.toByteArray());
        System.out.flush();
    }
}