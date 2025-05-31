package server;

import java.io.*;
import java.net.*;
import java.util.*;
import voting.VotingProtos.LoginRequest;
import voting.VotingProtos.LoginResponse;
import voting.VotingProtos.VoteRequest;
import voting.VotingProtos.VoteResponse;

public class TCPServer {
    private static final int PORT = 5000;
    private static boolean votingOpen = true;

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Servidor TCP iniciado na porta " + PORT);

        // Fecha votação após 5 minutos
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                votingOpen = false;
                System.out.println("Votação encerrada. Calculando resultados...");
                printResults();
            }
        }, 300000); // 5 minutos

        while (true) {
            Socket socket = serverSocket.accept();
            new ClientHandler(socket).start();
        }
    }

    static class ClientHandler extends Thread {
        private final Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try (
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())
            ) {
                // Processa login
                LoginRequest login = (LoginRequest) in.readObject();
                if (VoteManager.isValidVoter(login.getUsername(), login.getPassword())) {
                    LoginResponse response = LoginResponse.newBuilder()
                        .setSuccess(true)
                        .setMessage("Login bem-sucedido")
                        .addAllCandidates(VoteManager.getCandidates())
                        .build();
                    out.writeObject(response);

                    // Processa voto
                    if (votingOpen) {
                        VoteRequest vote = (VoteRequest) in.readObject();
                        boolean success = VoteManager.registerVote(vote.getVoterId(), vote.getCandidateId());
                        
                        VoteResponse.Builder voteResponse = VoteResponse.newBuilder()
                            .setSuccess(success);
                        
                        if (success) {
                            voteResponse.setMessage("Voto registrado com sucesso");
                        } else {
                            voteResponse.setMessage("Erro: Você já votou ou candidato inválido");
                        }
                        
                        out.writeObject(voteResponse.build());
                    } else {
                        out.writeObject(VoteResponse.newBuilder()
                            .setSuccess(false)
                            .setMessage("Votação encerrada")
                            .build());
                    }
                } else {
                    out.writeObject(LoginResponse.newBuilder()
                        .setSuccess(false)
                        .setMessage("Credenciais inválidas")
                        .build());
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void printResults() {
        Map<Integer, Integer> results = VoteManager.getResults();
        int totalVotes = results.values().stream().mapToInt(Integer::intValue).sum();
        
        System.out.println("\nResultados da votação:");
        for (Map.Entry<Integer, Integer> entry : results.entrySet()) {
            double percentage = totalVotes > 0 ? (entry.getValue() * 100.0) / totalVotes : 0;
            System.out.printf("Candidato %d: %d votos (%.2f%%)%n", 
                entry.getKey(), entry.getValue(), percentage);
        }
    }
}