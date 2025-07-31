package com.example.passagens_api.test;

import com.example.passagens_api.client.PassagemClient;
import com.example.passagens_api.model.PassagemClasseEconomica;
import com.example.passagens_api.model.PassagemPrimeiraClasse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.web.client.ResourceAccessException;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(OrderAnnotation.class)
public class PassagemApiTest {

    private PassagemClient client;
    private static final String VOO_PRIMEIRA = "V100";
    private static final String VOO_ECONOMICA = "V200";
    private static boolean serverRunning = false;

    @BeforeEach
    void setup() {
        // Use a URL padrão do seu servidor
        client = new PassagemClient("http://localhost:8080");
        
        // Verifica uma vez se o servidor está rodando
        if (!serverRunning) {
            try {
                // Tenta fazer uma requisição simples para verificar conexão
                client.reservarPrimeira(VOO_PRIMEIRA, "CHECK", "Teste", 0);
                serverRunning = true;
                System.out.println("\n✅ Servidor detectado em http://localhost:8080");
            } catch (ResourceAccessException e) {
                System.err.println("\n❌ ERRO FATAL: Servidor não está rodando!");
                System.err.println("Por favor, inicie o servidor primeiro com:");
                System.err.println("./mvnw spring-boot:run");
                System.err.println("Aguardando 5 segundos para você iniciar...");
                
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                
                // Tenta novamente
                try {
                    client.reservarPrimeira(VOO_PRIMEIRA, "CHECK", "Teste", 0);
                    serverRunning = true;
                } catch (Exception ex) {
                    throw new IllegalStateException(
                        "Servidor não está rodando. Inicie-o antes de executar os testes.", ex);
                }
            }
        }
    }

    @Test
    @Order(1)
    public void deveReservarPrimeiraClasse() {
        System.out.println("\n1. Testando reserva em Primeira Classe (V100)");
        
        PassagemPrimeiraClasse reserva = client.reservarPrimeira(
                VOO_PRIMEIRA, 
                "PC-001", 
                "Ana Souza", 
                2500.00
        );
        
        assertNotNull(reserva, "Reserva não deveria ser nula");
        assertEquals("PC-001", reserva.getCodigo());
        assertEquals("Ana Souza", reserva.getProprietario());
        assertEquals(2500.00, reserva.getPreco());
        assertEquals(VOO_PRIMEIRA, reserva.getVoo().getNumeroVoo());
        assertEquals("São Paulo", reserva.getVoo().getOrigem());
        assertEquals("Rio de Janeiro", reserva.getVoo().getDestino());
        
        System.out.println("✅ Reserva em Primeira Classe criada com sucesso");
    }

    @Test
    @Order(2)
    public void deveReservarClasseEconomica() {
        System.out.println("\n2. Testando reserva em Classe Econômica (V200)");
        
        PassagemClasseEconomica reserva = client.reservarEconomica(
                VOO_ECONOMICA, 
                "EC-001", 
                "Carlos Lima", 
                750.00, 
                22
        );
        
        assertNotNull(reserva, "Reserva não deveria ser nula");
        assertEquals("EC-001", reserva.getCodigo());
        assertEquals("Carlos Lima", reserva.getProprietario());
        assertEquals(750.00, reserva.getPreco());
        assertEquals(22, reserva.getAssento());
        assertEquals(VOO_ECONOMICA, reserva.getVoo().getNumeroVoo());
        assertEquals("Salvador", reserva.getVoo().getOrigem());
        assertEquals("Belo Horizonte", reserva.getVoo().getDestino());
        
        System.out.println("✅ Reserva em Classe Econômica criada com sucesso");
    }

    @Test
    @Order(3)
    public void deveTransferirPropriedade() {
        System.out.println("\n3. Testando transferência de propriedade");
        
        client.transferir(VOO_PRIMEIRA, "PC-001", "Fernanda Costa");
        
        // Verificação
        PassagemPrimeiraClasse reserva = client.reservarPrimeira(VOO_PRIMEIRA, "PC-001", "", 0);
        assertEquals("Fernanda Costa", reserva.getProprietario());
        
        System.out.println("✅ Propriedade transferida com sucesso");
    }

    @Test
    @Order(4)
    public void deveCancelarReserva() {
        System.out.println("\n4. Testando cancelamento de reserva");
        
        client.cancelar(VOO_ECONOMICA, "EC-001");
        
        // Verificação - deve lançar exceção
        assertThrows(org.springframework.web.client.HttpClientErrorException.BadRequest.class, () -> {
            client.reservarEconomica(VOO_ECONOMICA, "EC-001", "", 0, 0);
        });
        
        System.out.println("✅ Reserva cancelada com sucesso");
    }

    @Test
    @Order(5)
    public void deveRejeitarVooInexistente() {
        System.out.println("\n5. Testando voo inexistente");
        
        Exception exception = assertThrows(
            org.springframework.web.client.HttpClientErrorException.BadRequest.class, 
            () -> client.reservarPrimeira("V999", "INVALID", "Teste", 1000.00)
        );
        
        assertTrue(exception.getMessage().contains("V999"));
        
        System.out.println("✅ Sistema rejeitou voo inexistente corretamente");
    }
}
