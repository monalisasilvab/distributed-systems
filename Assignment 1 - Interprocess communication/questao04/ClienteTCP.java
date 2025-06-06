// ClienteTCP.java
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClienteTCP {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 12345);
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        DataInputStream in = new DataInputStream(socket.getInputStream());

        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite seu nome: ");
        String nome = scanner.nextLine();
        System.out.print("Digite seu email: ");
        String email = scanner.nextLine();

        Usuario usuario = new Usuario(1, nome, email);
        byte[] dados = ServidorTCP.serializarUsuario(usuario);

        // Enviar requisição
        out.writeInt(dados.length);
        out.write(dados);

        // Receber resposta
        int tamanhoResposta = in.readInt();
        byte[] resposta = new byte[tamanhoResposta];
        in.readFully(resposta);
        Usuario usuarioResposta = ServidorTCP.desserializarUsuario(resposta);

        System.out.println("Resposta do servidor: " + usuarioResposta);
        socket.close();
    }
}
