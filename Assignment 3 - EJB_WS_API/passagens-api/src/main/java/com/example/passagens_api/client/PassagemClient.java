package com.example.passagens_api.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestTemplate;
import com.example.passagens_api.model.PassagemClasseEconomica;
import com.example.passagens_api.model.PassagemPrimeiraClasse;

public class PassagemClient {

    private final RestTemplate rest = new RestTemplate();
    private final String baseUrl;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public PassagemClient(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    private String getApiUrl() {
        return baseUrl + "/api/passagens";
    }

    public PassagemPrimeiraClasse reservarPrimeira(String voo, String codigo, String prop, double preco) {
        ReservaRequest req = new ReservaRequest();
        req.numeroVoo = voo;
        req.codigo = codigo;
        req.proprietario = prop;
        req.preco = preco;

        String jsonResponse = rest.postForObject(getApiUrl() + "/primeira", req, String.class);
        return parseResponse(jsonResponse, PassagemPrimeiraClasse.class);
    }

    public PassagemClasseEconomica reservarEconomica(String voo, String codigo, String prop, double preco, int assento) {
        ReservaEconomicaRequest req = new ReservaEconomicaRequest();
        req.numeroVoo = voo;
        req.codigo = codigo;
        req.proprietario = prop;
        req.preco = preco;
        req.assento = assento;

        String jsonResponse = rest.postForObject(getApiUrl() + "/economica", req, String.class);
        return parseResponse(jsonResponse, PassagemClasseEconomica.class);
    }

    public void cancelar(String voo, String codigo) {
        rest.delete(getApiUrl() + "?numeroVoo={v}&codigo={c}", voo, codigo);
    }

    public void transferir(String voo, String codigo, String novoProp) {
        TransferRequest req = new TransferRequest();
        req.numeroVoo = voo;
        req.codigo = codigo;
        req.novoProprietario = novoProp;
        rest.put(getApiUrl() + "/transferir", req);
    }

    private <T> T parseResponse(String jsonResponse, Class<T> responseType) {
        try {
            
            System.out.println("🟡 JSON recebido da API:\n" + jsonResponse);

            // Verifica se é resposta com campo "message"
            JsonNode root = objectMapper.readTree(jsonResponse);
            if (root.has("message")) {
                String message = root.get("message").asText();
                return objectMapper.readValue(message, responseType);
            }

            // Tenta desserializar direto
            return objectMapper.readValue(jsonResponse, responseType);
        } catch (Exception e) {
            System.err.println("❌ Erro ao processar resposta JSON:");
            e.printStackTrace(); // mostra causa real
            throw new RuntimeException("Erro ao processar resposta", e);
        }
    }

    // DTOs internos
    public static class ReservaRequest {
        public String numeroVoo;
        public String codigo;
        public String proprietario;
        public double preco;
    }

    public static class ReservaEconomicaRequest extends ReservaRequest {
        public int assento;
    }

    public static class TransferRequest {
        public String numeroVoo;
        public String codigo;
        public String novoProprietario;
    }
}
