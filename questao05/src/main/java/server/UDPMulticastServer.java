package server;

import java.net.*;
import java.io.*;
import voting.VotingProtos.AdminCommand;
import voting.VotingProtos.AddCandidate;
import voting.VotingProtos.Candidate;
import voting.VotingProtos.RemoveCandidate;
import com.google.protobuf.InvalidProtocolBufferException;
import java.util.concurrent.*;
import java.util.Arrays;
import java.util.Enumeration;

public class UDPMulticastServer {
    private static final String MULTICAST_GROUP = "224.0.0.1";
    private static final int PORT = 4446;

    public static void main(String[] args) throws Exception {
        try (MulticastSocket socket = new MulticastSocket(PORT)) {
            InetAddress group = InetAddress.getByName(MULTICAST_GROUP);

            // Encontra uma interface de rede válida
            NetworkInterface networkInterface = null;

            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface ni = interfaces.nextElement();
                if (ni.isUp() && !ni.isLoopback()) {
                    networkInterface = ni;
                    break;
                }
            }

            if (networkInterface == null) {
                System.err.println("Erro: Nenhuma interface de rede válida encontrada.");
                return;
            }

            socket.joinGroup(new InetSocketAddress(group, PORT), networkInterface);
            System.out.println("Servidor multicast iniciado. Aguardando mensagens...");

            byte[] buffer = new byte[1024];

            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                if (packet.getLength() <= 0) {
                    System.out.println("Pacote vazio recebido. Ignorando.");
                    continue;
                }

                try {
                    byte[] data = Arrays.copyOf(packet.getData(), packet.getLength());
                    AdminCommand command = AdminCommand.parseFrom(data);

                    if (command.hasAddCandidate()) {
                        AddCandidate add = command.getAddCandidate();
                        Candidate c = add.getCandidate();
                        VoteManager.addCandidate(c.getId(), c.getName());
                        System.out.println("Candidato adicionado: " + c.getName());

                    } else if (command.hasRemoveCandidate()) {
                        int id = command.getRemoveCandidate().getCandidateId();
                        VoteManager.removeCandidate(id);
                        System.out.println("Candidato removido: ID " + id);

                    } else if (command.hasNotification()) {
                        String msg = command.getNotification();
                        System.out.println("Notificação recebida: " + msg);
                    }

                } catch (InvalidProtocolBufferException e) {
                    System.err.println("Erro ao parsear mensagem: Dados inválidos ou incompletos.");
                } catch (IOException e) {
                    System.err.println("Erro de rede ou IO: " + e.getMessage());
                }
            }
        } catch (SocketException e) {
            System.err.println("Erro ao criar socket multicast: " + e.getMessage());
        } catch (UnknownHostException e) {
            System.err.println("Grupo multicast desconhecido: " + e.getMessage());
        }
    }
}