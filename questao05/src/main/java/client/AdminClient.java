package client;

import java.net.*;

import voting.VotingProtos.AdminCommand;
import voting.VotingProtos.AddCandidate;
import voting.VotingProtos.Candidate;

public class AdminClient {
    private static final String MULTICAST_GROUP = "224.0.0.1";
    private static final int PORT = 4446;

    public static void main(String[] args) throws Exception {
        MulticastSocket socket = new MulticastSocket(PORT);
        InetAddress group = InetAddress.getByName(MULTICAST_GROUP);

        // Adiciona um novo candidato
        AddCandidate add = AddCandidate.newBuilder()
            .setCandidate(Candidate.newBuilder()
                .setId(4)
                .setName("Ana Costa"))
            .build();
        
        AdminCommand command = AdminCommand.newBuilder()
            .setAddCandidate(add)
            .build();

        DatagramPacket packet = new DatagramPacket(
            command.toByteArray(), command.getSerializedSize(), group, PORT);
        
        socket.send(packet);
        System.out.println("Comando de adição de candidato enviado");

        socket.close();
    }
}