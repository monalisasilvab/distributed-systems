package com.example.passagens_api.client;

import com.example.passagens_api.model.PassagemClasseEconomica;
import com.example.passagens_api.model.PassagemPrimeiraClasse;

public class TestePassagemClient {

    public static void main(String[] args) {
        PassagemClient cliente = new PassagemClient("http://localhost:8080");

        System.out.println("🟢 Iniciando testes do cliente...\n");

        // Teste 1: Reservar passagem de primeira classe
        try {
            System.out.println("Teste 1: Reservando passagem de primeira classe...");
            PassagemPrimeiraClasse passagem1 = cliente.reservarPrimeira("V100", "RES-001", "João Silva", 1500.00);
            System.out.println("✓ Passagem reservada: " + formatarPassagem(passagem1) + "\n");
        } catch (Exception e) {
            System.err.println("❌ Falha ao reservar passagem de primeira classe: " + e.getMessage());
        }

        // Teste 2: Reservar passagem econômica
        try {
            System.out.println("Teste 2: Reservando passagem econômica...");
            PassagemClasseEconomica passagem2 = cliente.reservarEconomica("V200", "RES-002", "Maria Santos", 650.00, 24);
            System.out.println("✓ Passagem reservada: " + formatarPassagem(passagem2) + "\n");
        } catch (Exception e) {
            System.err.println("❌ Falha ao reservar passagem econômica: " + e.getMessage());
        }

        // Teste 3: Transferir propriedade
        try {
            System.out.println("Teste 3: Transferindo propriedade...");
            cliente.transferir("V100", "RES-001", "Pedro Oliveira");
            System.out.println("✓ Transferência realizada com sucesso!\n");
        } catch (Exception e) {
            System.err.println("❌ Falha ao transferir passagem: " + e.getMessage());
        }

        // Teste 4: Cancelar passagem
        try {
            System.out.println("Teste 4: Cancelando passagem...");
            cliente.cancelar("V200", "RES-002");
            System.out.println("✓ Passagem cancelada com sucesso!\n");
        } catch (Exception e) {
            System.err.println("❌ Falha ao cancelar passagem: " + e.getMessage());
        }

        System.out.println("✅ TODOS OS TESTES FORAM EXECUTADOS!");
    }

    // Utilitário para exibir resumo da passagem
    private static String formatarPassagem(Object passagem) {
        if (passagem == null) return "(nula)";
        try {
            // Tenta imprimir campos comuns refletindo o objeto
            return passagem.getClass().getSimpleName() + " { " +
                    "codigo='" + passagem.getClass().getMethod("getCodigo").invoke(passagem) + "', " +
                    "proprietario='" + passagem.getClass().getMethod("getProprietario").invoke(passagem) + "', " +
                    "preco=" + passagem.getClass().getMethod("getPreco").invoke(passagem) +
                    " }";
        } catch (Exception e) {
            return "(erro ao formatar: " + e.getMessage() + ")";
        }
    }
}
