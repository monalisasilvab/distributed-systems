# Sistema de Votação Distribuída 🗳️

Este é um sistema de votação distribuída implementado com comunicação TCP/UDP, Protocol Buffers e suporte a notificações multicast para administradores.

---

## 🌐 Funcionalidades Principais

- **Login de eleitores** via TCP
- **Lista dinâmica de candidatos**
- **Contabilização de votos com limite de tempo**
- **Notificações em tempo real** via UDP multicast
- **Serialização de dados com Protocol Buffers**
- **Servidor multi-threaded**

---

## 📦 Requisitos

- Java 11+
- Protocol Buffers Compiler (`protoc`)
- Biblioteca `protobuf-java` (v3.21.12)

---

## 🔧 Instalação

```bash
# Instale Java
sudo apt update
sudo apt install openjdk-11-jdk

# Instale Protocol Buffers Compiler
sudo apt install protobuf-compiler

# Verifique a instalação
protoc --version  # Deve retornar libprotoc 3.12.x+

# Baixe a biblioteca protobuf-java
mkdir -p lib
wget https://repo1.maven.org/maven2/com/google/protobuf/protobuf-java/3.21.12/protobuf-java-3.21.12.jar -O lib/protobuf-java-3.21.12.jar
```

---

## 🧱 Estrutura do Projeto

```
questao05/
├── proto/
│   └── voting.proto         # Definição do Protocol Buffer
├── src/
│   └── main/
│       └── java/
│           ├── client/      # Clientes (Voter.java, AdminClient.java)
│           ├── server/      # Servidores (TCPServer.java, UDPMulticastServer.java, VoteManager.java)
│           └── voting/      # Classes geradas pelo Protocol Buffers
├── build/                    # Classes compiladas
├── lib/
│   └── protobuf-java-3.21.12.jar
├── run.sh                    # Script de execução
└── README.md
```

---

## 🛠️ Compilação e Execução

### 1. Gere as classes do Protocol Buffer
```bash
protoc --java_out=src/main/java proto/voting.proto
```

### 2. Compile o projeto
```bash
mkdir -p build
javac -d build \
  -cp lib/protobuf-java-3.21.12.jar \
  src/main/java/client/*.java \
  src/main/java/server/*.java \
  src/main/java/voting/*.java
```

### 3. Execute os componentes

#### Servidor TCP (porta 5000)
```bash
cd build
java -cp .:../lib/protobuf-java-3.21.12.jar server.TCPServer
```

#### Servidor UDP Multicast (porta 4446)
```bash
cd build
java -cp .:../lib/protobuf-java-3.21.12.jar server.UDPMulticastServer
```

#### Cliente Eleitor
```bash
cd build
java -cp .:../lib/protobuf-java-3.21.12.jar client.Voter
```

#### Cliente Administrador
```bash
cd build
java -cp .:../lib/protobuf-java-3.21.12.jar client.AdminClient
```

---

## 🧪 Credenciais Pré-Configuradas

| Tipo        | Usuário     | Senha    |
|------------|-------------|----------|
| Eleitor    | `eleitor1`  | `senha1` |
| Eleitor    | `eleitor2`  | `senha2` |

---

## 📢 Notificações Administrativas

O servidor UDP multicast permite que administradores enviem notificações para todos os clientes conectados. Exemplo:

```java
// AdminClient.java
AdminCommand command = AdminCommand.newBuilder()
    .setNotification("Nova atualização: Eleição em andamento.")
    .build();
```

---

## 📊 Resultados da Votação

Após 5 minutos, o servidor encerra a votação e exibe:

```
Resultados da votação:
Candidato 1: 3 votos (30.00%)
Candidato 2: 5 votos (50.00%)
Candidato 3: 2 votos (20.00%)
```

---

## 🛠️ Problemas Comuns e Soluções

### Erro: Interface de rede não encontrada
- **Causa:** Problema com interface de rede no servidor UDP
- **Solução:** Atualize o código do `UDPMulticastServer.java` para usar `NetworkInterface.getNetworkInterfaces()` (verifique o histórico de commits)

### Erro: Classe não encontrada
- **Causa:** Estrutura de pacotes incorreta
- **Solução:** Garanta que os `.java` tenham `package client;`, `package server;`, etc.

### Erro: Protocol Buffer
- **Causa:** Incompatibilidade entre cliente/servidor
- **Solução:** Recompile o `.proto` e garanta que ambos usam a mesma versão

