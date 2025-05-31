package server;

import java.util.concurrent.*;
import java.util.*;
import voting.VotingProtos.Candidate;

public class VoteManager {
    // Lista de candidatos (thread-safe)
    private static final ConcurrentMap<Integer, String> candidates = new ConcurrentHashMap<>();
    
    // Votos por candidato
    private static final ConcurrentMap<Integer, Integer> voteCounts = new ConcurrentHashMap<>();
    
    // Eleitores que já votaram
    private static final Set<String> votersWhoVoted = Collections.synchronizedSet(new HashSet<>());
    
    // Credenciais dos eleitores
    private static final Map<String, String> voterCredentials = new HashMap<>();

    static {
        // Inicializa alguns eleitores de exemplo
        voterCredentials.put("eleitor1", "senha1");
        voterCredentials.put("eleitor2", "senha2");

        // Inicializa candidatos de exemplo
        candidates.put(1, "João Silva");
        candidates.put(2, "Maria Oliveira");
        candidates.put(3, "Carlos Souza");

        // Inicializa contagem de votos
        voteCounts.put(1, 0);
        voteCounts.put(2, 0);
        voteCounts.put(3, 0);
    }

    public static boolean isValidVoter(String username, String password) {
        return voterCredentials.containsKey(username) && 
               voterCredentials.get(username).equals(password);
    }

    public static List<Candidate> getCandidates() {
        List<Candidate> candidateList = new ArrayList<>();
        for (Map.Entry<Integer, String> entry : candidates.entrySet()) {
            candidateList.add(Candidate.newBuilder()
                .setId(entry.getKey())
                .setName(entry.getValue())
                .build());
        }
        return candidateList;
    }

    public static boolean hasAlreadyVoted(String voterId) {
        return votersWhoVoted.contains(voterId);
    }

    public static boolean registerVote(String voterId, int candidateId) {
        if (!candidates.containsKey(candidateId)) return false;
        if (hasAlreadyVoted(voterId)) return false;

        votersWhoVoted.add(voterId);
        voteCounts.put(candidateId, voteCounts.getOrDefault(candidateId, 0) + 1);
        return true;
    }

    public static void addCandidate(int id, String name) {
        candidates.put(id, name);
        voteCounts.put(id, 0);
    }

    public static void removeCandidate(int id) {
        candidates.remove(id);
        voteCounts.remove(id);
    }

    public static Map<Integer, Integer> getResults() {
        return new HashMap<>(voteCounts);
    }
}