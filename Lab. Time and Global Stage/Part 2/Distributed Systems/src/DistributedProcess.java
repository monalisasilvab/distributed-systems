import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DistributedProcess implements Runnable {
    private final int id;
    private final int[] peerPorts;
    private final Random rnd = new Random();
    private DatagramSocket socket;

    // Relógio lógico de Lamport
    private int lamportClock = 0;

    // Regex para extrair L(mensagem)
    private static final Pattern PATTERN_L = Pattern.compile("\\[L=(\\d+)]");

    public DistributedProcess(int id, int port, int[] peerPorts) throws SocketException {
        this.id = id;
        this.peerPorts = peerPorts;
        this.socket = new DatagramSocket(port);
    }
    @Override
    public void run() {
        // Thread de recepção
        new Thread(this::receiveLoop).start();

        while (true) {
            try {
                // -- Evento interno antes do envio --
                lamportClock++;

                // Sleep randômico
                long pause = 500 + rnd.nextInt(1500);
                Thread.sleep(pause);

                // Escolher peer
                int peerPort = peerPorts[rnd.nextInt(peerPorts.length)];

                // Texto da mensagem inclui L e timestamp físico
                String msg = String.format("PING from %d [L=%d] @phys=%d",
                              id, lamportClock, System.currentTimeMillis());
                byte[] buf = msg.getBytes(StandardCharsets.UTF_8);
                DatagramPacket packet = new DatagramPacket(buf, buf.length,
                        InetAddress.getLocalHost(), peerPort);
                socket.send(packet);

                // Log de envio
                System.out.printf(
                  "[P%d] SEND → P@%d | phys=%d | lamport=%d | \"%s\"%n",
                  id, peerPort, System.currentTimeMillis(), lamportClock, msg);

            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
    }

    private void receiveLoop() {
        byte[] buf = new byte[512];
        while (true) {
            try {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);

                long physRecv = System.currentTimeMillis();
                String msg = new String(packet.getData(), 0, packet.getLength(), StandardCharsets.UTF_8);

                // Parse do Lamport recebido
                Matcher m = PATTERN_L.matcher(msg);
                int receivedL = m.find() ? Integer.parseInt(m.group(1)) : 0;

                // Atualização do relógio lógico no recebimento
                lamportClock = Math.max(lamportClock, receivedL) + 1;

                // Log de recebimento
                System.out.printf(
                  "[P%d] RECV ← %s:%d | phys=%d | rec_L=%d | new_L=%d | \"%s\"%n",
                  id,
                  packet.getAddress().getHostAddress(),
                  packet.getPort(),
                  physRecv,
                  receivedL,
                  lamportClock,
                  msg
                );

            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public static void main(String[] args) throws Exception {
        int[] ports = {5000, 5001, 5002};
        for (int i = 0; i < ports.length; i++) {
            final int idx = i;
            int[] peers = java.util.Arrays.stream(ports)
                                .filter(p -> p != ports[idx])
                                .toArray();
            DistributedProcess dp = new DistributedProcess(idx, ports[idx], peers);
            new Thread(dp, "Proc-" + idx).start();
        }
    }
}
