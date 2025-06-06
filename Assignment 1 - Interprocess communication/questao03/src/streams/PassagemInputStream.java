package streams;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import model.Passagem;
import model.PassagemClasseEconomica;
import model.Voo;

public class PassagemInputStream extends InputStream {
    private final DataInputStream dataIn;
    private final InputStream origem;

    public PassagemInputStream(InputStream origem) {
        this.origem = origem;
        this.dataIn = new DataInputStream(origem);
    }

    @Override
    public int read() throws IOException {
        return dataIn.read();
    }

    public List<Passagem> lerPassagens() throws IOException {
        List<Passagem> passagens = new ArrayList<>();
        
        // Lê o número de passagens
        int numPassagens = dataIn.readInt();
        
        // Voo genérico para associação
        Voo vooGen = new Voo("V000", "Desconhecida", "Desconhecida");
        
        for (int i = 0; i < numPassagens; i++) {
            // Lê os tamanhos dos atributos
            int tamCodigo = dataIn.readInt();
            int tamProprietario = dataIn.readInt();
            int tamPreco = dataIn.readInt();
            
            // Lê os bytes dos atributos
            byte[] codigoBytes = new byte[tamCodigo];
            dataIn.readFully(codigoBytes);
            
            byte[] proprietarioBytes = new byte[tamProprietario];
            dataIn.readFully(proprietarioBytes);
            
            byte[] precoBytes = new byte[tamPreco];
            dataIn.readFully(precoBytes);
            
            // Converte para objetos Java
            String codigo = new String(codigoBytes, "UTF-8");
            String proprietario = new String(proprietarioBytes, "UTF-8");
            double preco = Double.parseDouble(new String(precoBytes, "UTF-8"));
            
            // Cria passagem genérica
            Passagem passagem = new PassagemClasseEconomica(
                codigo, proprietario, vooGen, preco, false, 0
            );
            passagens.add(passagem);
        }
        return passagens;
    }

    @Override
    public void close() throws IOException {
        dataIn.close();
        origem.close();
    }
}