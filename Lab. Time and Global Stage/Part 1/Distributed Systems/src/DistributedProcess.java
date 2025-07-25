import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.Arrays;
import java.util.stream.Collectors;

public class DistributedProcess implements Runnable {
    private final int id;
    private final int N;         
    private final int port;
    private final int[] peerPorts;
    private DatagramSocket socket;
    private final Random rnd = new Random();

    // Vetor de relógio: tamanho N
    private final int[] V;

    public DistributedProcess(int id, int[] allPorts) throws SocketException {
        this.id = id;
        this.N = allPorts.length;
        this.port = allPorts[id];
        this.peerPorts = Arrays.stream(allPorts)
                               .filter(p -> p != port)
                               .toArray();
        this.socket = new DatagramSocket(port);
        this.V = new int[N];
        // já inicializado com zeros
    }
    
    private void sendPing() throws Exception {
        // 1) Evento local: incrementa a própria posição
        V[id]++;

        // 2) Prepara payload: converte vetor em string "V0,V1,...,Vn"
        String vectorStr = Arrays.stream(V)
                                .mapToObj(Integer::toString)
                                .collect(Collectors.joining(","));
        String msg = String.format("MSG from %d | V=[%s] | phys=%d", 
                                    id, vectorStr, System.currentTimeMillis());

        byte[] buf = msg.getBytes(StandardCharsets.UTF_8);
        int peerPort = peerPorts[rnd.nextInt(peerPorts.length)];
        DatagramPacket packet = new DatagramPacket(buf, buf.length,
                                                InetAddress.getLocalHost(),
                                                peerPort);
        socket.send(packet);

        System.out.printf("[P%d] SEND → P@%d | phys=%d | V=%s%n",
                        id, peerPort, System.currentTimeMillis(), vectorStr);
    }

    private void receiveLoop() {
        byte[] buf = new byte[1024];
        while (true) {
            try {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);

                long physRecv = System.currentTimeMillis();
                String fullMsg = new String(packet.getData(), 0, packet.getLength(),
                                            StandardCharsets.UTF_8);

                // Extrai vetor do payload: assume "V=[0,2,1]" no texto
                String vecPart = fullMsg.replaceAll(".*V=\\[(.*?)] .*", "$1");
                String[] parts = vecPart.split(",");
                int[] Vmsg = new int[N];
                for (int j = 0; j < N; j++) {
                    Vmsg[j] = Integer.parseInt(parts[j]);
                }

                // 1) Atualiza componente a componente
                for (int j = 0; j < N; j++) {
                    V[j] = Math.max(V[j], Vmsg[j]);
                }
                // 2) Evento local de recepção
                V[id]++;

                // Log de recebimento
                String vectorStr = Arrays.stream(V)
                                        .mapToObj(Integer::toString)
                                        .collect(Collectors.joining(","));
                System.out.printf("[P%d] RECV ← %s:%d | phys=%d | after V=[%s] | \"%s\"%n",
                                id,
                                packet.getAddress().getHostAddress(),
                                packet.getPort(),
                                physRecv,
                                vectorStr,
                                fullMsg);

            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
    }

    @Override
    public void run() {
        new Thread(this::receiveLoop).start();
        while (true) {
            try {
                // Sleep randômico
                Thread.sleep(500 + rnd.nextInt(1500));
                sendPing();
            } catch (Exception ex) {
                ex.printStackTrace();
                break;
            }
        }
    }

    public static void main(String[] args) throws Exception {
        int[] ports = {5000, 5001, 5002};
        for (int i = 0; i < ports.length; i++) {
            DistributedProcess dp = new DistributedProcess(i, ports);
            new Thread(dp, "Proc-" + i).start();
        }
    }

}
