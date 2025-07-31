package com.example.passagens_api.service;

import com.example.passagens_api.model.*;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PassagemService {
    
    private final Map<String, Voo> voos = new ConcurrentHashMap<>();

    public PassagemService() {
        voos.put("V100", new Voo("V100", "São Paulo", "Rio de Janeiro"));
        voos.put("V200", new Voo("V200", "Salvador", "Belo Horizonte"));
    }

    public PassagemPrimeiraClasse reservarPrimeira(String numeroVoo, String codigo, String proprietario, double preco) {
        Voo voo = voos.get(numeroVoo);
        PassagemPrimeiraClasse passagem = new PassagemPrimeiraClasse(voo, codigo, proprietario, preco);
        voo.addPassagem(passagem); // CORREÇÃO: Adiciona a passagem ao voo
        return passagem;
    }

    public PassagemClasseEconomica reservarEconomica(String numeroVoo, String codigo, String proprietario, double preco, int assento) {
        Voo voo = voos.get(numeroVoo);
        PassagemClasseEconomica passagem = new PassagemClasseEconomica(voo, codigo, proprietario, preco, assento);
        voo.addPassagem(passagem); // CORREÇÃO: Adiciona a passagem ao voo
        return passagem;
    }

    public void cancelarReserva(String numeroVoo, String codigo) {
        Voo voo = voos.get(numeroVoo);
        if (voo != null) {
            voo.getPassagens().stream()
               .filter(p -> p.getCodigo().equals(codigo))
               .findFirst()
               .ifPresent(voo::removePassagem);
        }
    }

    public void transferirPassagem(String numeroVoo, String codigo, String novoProprietario) {
        Voo voo = voos.get(numeroVoo);
        if (voo != null) {
            voo.getPassagens().stream()
               .filter(p -> p.getCodigo().equals(codigo))
               .findFirst()
               .ifPresent(p -> p.setProprietario(novoProprietario));
        }
    }
}