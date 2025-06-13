# Sistema de Controle de Passagens Aéreas via RMI

Descrição
Este projeto implementa um sistema de reserva de passagens aéreas distribuído usando Java RMI. O cliente pode reservar, cancelar e transferir passagens de forma transparente como se chamasse métodos locais.

## Pré-requisitos

    Java Development Kit (JDK) instalado (versão 8 ou superior)

    Ambiente configurado com variáveis de ambiente JAVA_HOME e PATH apontando para o JDK

## Estrutura do Projeto

    src/
    ├── interfaces/      # Interfaces remotas e de transferência
    ├── model/           # Classes de modelo (POJOs)
    ├── server/          # Implementação do servidor RMI
    └── client/          # Cliente RMI para testes

## Como executar

### 1. Compile todas as classes:
    javac -d bin src/interfaces/*.java src/model/*.java src/server/*.java src/client/*.java

### 2. Inicie o servidor RMI:
    java -cp bin server.ServidorRMI

### 3. Execute o cliente em outra janela de terminal:
    java -cp bin client.ClienteRMI

## Saida esperada do cliente
    Reservas realizadas:
    - PC-001: João Silva
    - EC-205: Maria Souza

    Passagem transferida: PC-001 -> Carlos Oliveira

    Reserva cancelada: EC-205   

## Atendimento aos requisitos
**1. Quatro classes entidades:**

    Passagem

    PassagemPrimeiraClasse (extensão de Passagem)

    PassagemClasseEconomica (extensão de Passagem)

    Voo

**2. Agregações ("tem-um"):**

    Voo possui múltiplas Passagem

    Passagem referencia um Voo  

**3. Extensões ("é-um"):**

    PassagemPrimeiraClasse é um Passagem

    PassagemClasseEconomica é um Passagem

**4. Quatro métodos remotos:**

    reservarPassagemPrimeiraClasse()

    reservarPassagemEconomica()

    cancelarReserva()

    transferirPassagem()

**5. Passagem por referência:**

    Os stubs de objetos remotos (interfaces RMI) são passados por referência.

**6. Passagem por valor:**

    Objetos locais das classes Passagem e Voo são serializados e transmitidos por valor.

## Protocolo de Requisição-Resposta via RMI
**Cliente:**

    Invoca métodos remotos através do stub RMI como chamadas de método locais.

    A serialização Java trata da conversão de objetos em formato de bytes.

**Servidor:**

    Implementa o esqueleto RMI que recebe requisições (getRequest) e envia respostas (sendReply).

    Processa a lógica de negócios de reservas, transferências e cancelamentos.

**Gerenciamento de Mensagens:**
    
    RMI gerencia IDs de requisição, retransmissões, confirmações e histórico de respostas automaticamente.

## Solução de Problemas comuns

**1. Erro "Connection refused":**

    Verifique se o servidor RMI está em execução

    Confira se o firewall não está bloqueando a porta 1099

**2. Erros de serialização:**

    Certifique-se que todas as classes transferidas implementam Serializable

    Adicione serialVersionUID às classes serializáveis

**3.Erros de compilação no Windows:**

    Use comandos de compilação separados para cada pacote

    Evite usar wildcards (*) em caminhos de diretórios

**4.Erro "ClassNotFoundException":**

    Verifique se todas as classes foram compiladas

    Confira o classpath (-cp bin) nos comandos de execução