package com.example.passagens_api.controller;

import com.example.passagens_api.model.*;
import com.example.passagens_api.service.PassagemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/passagens")
public class PassagemController {
    private final PassagemService service;
    
    public PassagemController(PassagemService service) { 
        this.service = service; 
    }

    @PostMapping("/primeira")
    public ResponseEntity<PassagemPrimeiraClasse> reservarPrimeira(@RequestBody ReservaRequest req) {
        var p = service.reservarPrimeira(req.numeroVoo, req.codigo, req.proprietario, req.preco);
        return ResponseEntity.ok(p);
    }

    @PostMapping("/economica")
    public ResponseEntity<PassagemClasseEconomica> reservarEconomica(@RequestBody ReservaEconomicaRequest req) {
        var p = service.reservarEconomica(req.numeroVoo, req.codigo, req.proprietario, req.preco, req.assento);
        return ResponseEntity.ok(p);
    }

    @DeleteMapping
    public ResponseEntity<Void> cancelar(@RequestParam String numeroVoo, @RequestParam String codigo) {
        service.cancelarReserva(numeroVoo, codigo);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/transferir")
    public ResponseEntity<Void> transferir(@RequestBody TransferRequest req) {
        service.transferirPassagem(req.numeroVoo, req.codigo, req.novoProprietario);
        return ResponseEntity.ok().build();
    }

    // DTOs CORRIGIDOS
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