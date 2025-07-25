import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

public class DistributedProcess implements Runnable {
    private final int id;
    private final int N;
    private final int port;
    private final int[] peerPorts;
    private final DatagramSocket socket;
    private final Random rnd = new Random();
    private final int[] V;

    public DistributedProcess(int id, int[] allPorts) throws Exception {
        this.id = id;
        this.N = allPorts.length;
        this.port = allPorts[id];
        this.peerPorts = Arrays.stream(allPorts)
                               .filter(p -> p != this.port)
                               .toArray();
        this.socket = new DatagramSocket(this.port);
        this.V = new int[N]; // inicializa com zeros
    }

    @Override
    public void run() {
        new Thread(this::receiveLoop).start();
        while (true) {
            try {
                // Evento local antes do envio
                V[id]++;

                // Aguarda tempo aleatório entre 500ms e 2000ms
                Thread.sleep(500 + rnd.nextInt(1500));

                sendVectorMessage();
            } catch (Exception ex) {
                ex.printStackTrace();
                break;
            }
        }
    }

    private void sendVectorMessage() throws Exception {
        // Prepara vetor como string "v0,v1,..."
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

        System.out.printf("[P%d] SEND → P@%d | phys=%d | V=[%s]%n",
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

                // Extrai parte do vetor: assume "V=[x,y,z]"
                String vecPart = fullMsg.replaceAll(".*V=\\[(.*?)] .*", "$1");
                String[] parts = vecPart.split(",");
                int[] Vmsg = new int[N];
                for (int j = 0; j < N; j++) {
                    Vmsg[j] = Integer.parseInt(parts[j]);
                }

                // Atualiza elemento a elemento
                for (int j = 0; j < N; j++) {
                    V[j] = Math.max(V[j], Vmsg[j]);
                }
                // Evento local de recepção
                V[id]++;

                String vectorStr = Arrays.stream(V)
                                         .mapToObj(Integer::toString)
                                         .collect(Collectors.joining(","));
                System.out.printf("[P%d] RECV ← %s:%d | phys=%d | V=[%s] | \"%s\"%n",
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

    public static void main(String[] args) throws Exception {
        int[] ports = {5000, 5001, 5002};
        for (int i = 0; i < ports.length; i++) {
            DistributedProcess dp = new DistributedProcess(i, ports);
            new Thread(dp, "Proc-" + i).start();
        }
    }
}
