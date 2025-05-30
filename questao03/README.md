# Projeto: Leitura de Objetos via InputStream — Questão 03

Este projeto complementa a funcionalidade de transmissão de objetos com uma subclasse de `InputStream` chamada `PassagemInputStream`, responsável por ler dados serializados de objetos POJO transmitidos em formato binário (como feito na Questão 02). Os dados podem ser lidos da entrada padrão, de arquivos ou de uma conexão TCP.

## 🎯 Objetivo da Atividade

- Criar a subclasse `PassagemInputStream`, que estende `InputStream`.
- O construtor da subclasse deve receber como parâmetro um `InputStream` de origem.
- Realizar testes de leitura com diferentes fontes:
  - Entrada padrão (`System.in`)
  - Arquivo (`FileInputStream`)
  - Conexão TCP (`Socket.getInputStream()`)

## 🗂️ Estrutura de Arquivos

    questao03/
    ├── bin/ # Arquivos compilados (.class)
    ├── data/ # Dados gerados em testes
    │ └── teste.bin
    ├── src/ # Código-fonte principal
    │ ├── interfaces/
    │ │ └── Transferivel.java
    │ ├── model/
    │ │ ├── Passagem.java
    │ │ ├── PassagemClasseEconomica.java
    │ │ ├── PassagemPrimeiraClasse.java
    │ │ └── Voo.java
    │ ├── services/
    │ │ ├── ServicoReservas.java
    │ │ └── ServicoTransferencias.java
    │ ├── streams/
    │ │ ├── PassagemInputStream.java
    │ │ └── PassagemOutputStream.java
    │ └── SistemaAereoApp.java
    ├── test/ # Código de teste
    │ ├── GeradorBinarioTeste.java
    │ ├── ServidorTCPTeste.java
    │ └── TestePassagemInputStream.java
    └── README.md


## 🛠️ Compilação

Compile todos os arquivos a partir da raiz do projeto:

```bash
cd questao03

javac -d bin src/*.java src/interfaces/*.java src/model/*.java src/services/*.java src/streams/*.java test/*.java
```

## ▶️ Execução dos Testes  

### 1️⃣ Inicie o servidor TCP (em um terminal separado)  
```bash
java -cp bin test.ServidorTCPTeste
```

### 2️⃣ Execute os testes (em outro terminal) 
a. Teste com entrada padrão
```bash
# Gerar arquivo binário de teste
java -cp bin test.GeradorBinarioTeste > data/teste.bin

# Executar leitura a partir da entrada padrão (usando redirecionamento)
java -cp bin test.TestePassagemInputStream < data/teste.bin
```

b. Teste com arquivo
```bash
java -cp bin test.TestePassagemInputStream
```
c. Teste com servidor TCP
```bash
java -cp bin test.TestePassagemInputStream

```

## ✅ Saída Esperada

Número de passagens: 3

Passagem 1:  
Código: PC-001  
Proprietário: João Silva  
Preço: 8500.0  
  
Passagem 2:  
Código: EC-205  
Proprietário: Maria Souza  
Preço: 2200.0  
  
Passagem 3:  
Código: PC-002  
Proprietário: Carlos Oliveira  
Preço: 9200.0  