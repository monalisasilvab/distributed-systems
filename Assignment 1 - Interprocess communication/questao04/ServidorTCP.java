// ServidorTCP.java
import java.io.*;
import java.net.*;
import java.util.*;

public class ServidorTCP {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(12345);
        System.out.println("Servidor TCP rodando...");

        while (true) {
            Socket socket = serverSocket.accept();
            new Thread(new Processador(socket)).start();
        }
    }

    static class Processador implements Runnable {
        private Socket socket;

        public Processador(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (
                DataInputStream in = new DataInputStream(socket.getInputStream());
                DataOutputStream out = new DataOutputStream(socket.getOutputStream())
            ) {
                // Desserialização da requisição
                int tamanho = in.readInt();
                byte[] dados = new byte[tamanho];
                in.readFully(dados);

                Usuario user = desserializarUsuario(dados);
                System.out.println("Recebido: " + user);

                // Resposta do servidor (ecoar o usuário)
                byte[] resposta = serializarUsuario(user);
                out.writeInt(resposta.length);
                out.write(resposta);

                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Serialização manual
    public static byte[] serializarUsuario(Usuario user) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(user.getId());
        dos.writeUTF(user.getNome());
        dos.writeUTF(user.getEmail());
        return baos.toByteArray();
    }

    // Desserialização manual
    public static Usuario desserializarUsuario(byte[] dados) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(dados);
        DataInputStream dis = new DataInputStream(bais);
        int id = dis.readInt();
        String nome = dis.readUTF();
        String email = dis.readUTF();
        return new Usuario(id, nome, email);
    }
}
