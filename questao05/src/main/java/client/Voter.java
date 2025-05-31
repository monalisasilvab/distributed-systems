package client;

import java.io.*;
import java.net.*;
import java.util.*;

import voting.VotingProtos.LoginRequest;
import voting.VotingProtos.LoginResponse;
import voting.VotingProtos.Candidate;
import voting.VotingProtos.VoteRequest;
import voting.VotingProtos.VoteResponse;

public class Voter {
    private static final String SERVER_IP = "127.0.0.1"; // IP do servidor
    private static final int TCP_PORT = 5000;            // Porta TCP
    private static final int MAX_RETRIES = 3;            // Tentativas de conexão

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Bem-vindo ao sistema de votação!");

        // Solicita credenciais do eleitor
        System.out.print("Digite seu nome de usuário: ");
        String username = scanner.nextLine();

        System.out.print("Digite sua senha: ");
        String password = scanner.nextLine();

        try {
            // Conectar ao servidor TCP
            Socket socket = new Socket(SERVER_IP, TCP_PORT);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            // Enviar login
            LoginRequest login = LoginRequest.newBuilder()
                .setUsername(username)
                .setPassword(password)
                .build();
            out.writeObject(login);

            // Receber resposta do login
            LoginResponse loginResponse = (LoginResponse) in.readObject();
            if (!loginResponse.getSuccess()) {
                System.out.println("Erro no login: " + loginResponse.getMessage());
                return;
            }

            System.out.println("Login bem-sucedido!");
            System.out.println("Lista de candidatos disponíveis:");

            // Mostrar candidatos
            List<Candidate> candidates = loginResponse.getCandidatesList();
            for (Candidate c : candidates) {
                System.out.println(c.getId() + " - " + c.getName());
            }

            // Solicitar voto
            System.out.print("Digite o ID do candidato em que deseja votar: ");
            int candidateId = Integer.parseInt(scanner.nextLine());

            // Enviar voto
            VoteRequest vote = VoteRequest.newBuilder()
                .setCandidateId(candidateId)
                .setVoterId(username)
                .build();
            out.writeObject(vote);

            // Receber resposta do voto
            VoteResponse voteResponse = (VoteResponse) in.readObject();
            System.out.println("Resultado do voto: " + voteResponse.getMessage());

            // Fechar conexões
            in.close();
            out.close();
            socket.close();

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro de comunicação com o servidor: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("ID do candidato inválido.");
        }
    }
}