package com.example.passagens_api.client; 

import org.springframework.web.client.RestTemplate;
import com.example.passagens_api.controller.PassagemController.ReservaRequest;
import com.example.passagens_api.controller.PassagemController.TransferRequest;
import com.example.passagens_api.controller.PassagemController.ReservaEconomicaRequest;
import com.example.passagens_api.model.PassagemClasseEconomica;
import com.example.passagens_api.model.PassagemPrimeiraClasse;

public class PassagemClient {
    
    private final RestTemplate rest = new RestTemplate();
    private final String base = "http://localhost:8080/api/passagens";

    public PassagemPrimeiraClasse reservarPrimeira(String voo, String codigo, String prop, double preco) {
        ReservaRequest req = new ReservaRequest();
        req.numeroVoo = voo;
        req.codigo = codigo;
        req.proprietario = prop;
        req.preco = preco;
        return rest.postForObject(base + "/primeira", req, PassagemPrimeiraClasse.class);
    }

    public PassagemClasseEconomica reservarEconomica(String voo, String codigo, String prop, double preco, int assento) {
        ReservaEconomicaRequest req = new ReservaEconomicaRequest();
        req.numeroVoo = voo;
        req.codigo = codigo;
        req.proprietario = prop;
        req.preco = preco;
        req.assento = assento;
        return rest.postForObject(base + "/economica", req, PassagemClasseEconomica.class);
    }

    public void cancelar(String voo, String codigo) {
        rest.delete(base + "?numeroVoo={v}&codigo={c}", voo, codigo);
    }

    public void transferir(String voo, String codigo, String novoProp) {
        TransferRequest req = new TransferRequest();
        req.numeroVoo = voo;
        req.codigo = codigo;
        req.novoProprietario = novoProp;
        rest.put(base + "/transferir", req);
    }
}
