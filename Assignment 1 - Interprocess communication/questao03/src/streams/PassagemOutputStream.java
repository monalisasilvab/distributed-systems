package streams;

import java.io.*;
import model.Passagem;

public class PassagemOutputStream extends OutputStream {
    private final Passagem[] passagens;
    private final int numPassagens;
    private final OutputStream destino;
    private final DataOutputStream dataOut;

    public PassagemOutputStream(Passagem[] passagens, int numPassagens, OutputStream destino) {
        this.passagens = passagens;
        this.numPassagens = numPassagens;
        this.destino = destino;
        this.dataOut = new DataOutputStream(destino);
    }

    @Override
    public void write(int b) throws IOException {
        destino.write(b);
    }

    public void enviarPassagens() throws IOException {
        // Escreve o número de passagens
        dataOut.writeInt(numPassagens);
        
        for (int i = 0; i < numPassagens; i++) {
            Passagem p = passagens[i];
            if (p == null) continue;
            
            // Serializa 3 atributos: código, proprietário e preço
            byte[] codigoBytes = p.getCodigo().getBytes("UTF-8");
            byte[] proprietarioBytes = p.getProprietario().getBytes("UTF-8");
            byte[] precoBytes = String.valueOf(p.getPreco()).getBytes("UTF-8");
            
            // Escreve os tamanhos dos atributos
            dataOut.writeInt(codigoBytes.length);
            dataOut.writeInt(proprietarioBytes.length);
            dataOut.writeInt(precoBytes.length);
            
            // Escreve os dados dos atributos
            dataOut.write(codigoBytes);
            dataOut.write(proprietarioBytes);
            dataOut.write(precoBytes);
        }
        dataOut.flush();
    }

    @Override
    public void close() throws IOException {
        dataOut.close();
        destino.close();
    }
}