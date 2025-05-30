# Projeto: Sistema de Passagens Aéreas — Transmissão via OutputStream

Este módulo amplia o projeto de passagens aéreas ao transformar uma das classes POJO em uma subclasse de `OutputStream`, permitindo o envio de dados de objetos através de diferentes canais: saída padrão, arquivo e conexão TCP.

## 📌 Objetivo da Atividade

- Escolher uma classe POJO do serviço remoto já implementado.
- Criar uma subclasse `PojoEscolhidoOutputStream` que herda de `OutputStream` para serializar e transmitir objetos.
- O construtor da subclasse deve receber:
  1. Um array de objetos (POJOs) contendo os dados.
  2. O número de objetos que serão transmitidos.
  3. A quantidade de bytes para representar pelo menos **3 atributos** de cada objeto.
  4. Um `OutputStream` de destino (padrão, arquivo ou socket TCP).

## 🗂️ Estrutura do Projeto

    projeto-passagens-aereas/
    ├── src/
    │ ├── interfaces/ # Interfaces do sistema
    │ ├── model/ # POJOs e OutputStream personalizado
    │ ├── services/ # Lógica dos serviços
    │ ├── test/ # Classes de teste (inclui ServidorTCPTeste.java)
    │ └── SistemaAereoApp.java # Classe principal
    ├── bin/ # Diretório de saída da compilação
    ├── passagens.dat # Arquivo de saída binária (gerado)

## 🛠️ Compilação

Navegue até a raiz do projeto e compile todos os arquivos:

```bash
cd questao02    

javac -d bin src/*.java src/interfaces/*.java src/model/*.java src/services/*.java
```

## ▶️ Execução

1. Servidor TCP (executar em terminal separado)

```bash
java -cp bin test.ServidorTCPTeste
```

2. Testes de Transmissão (executar em outro terminal)

```bash
java -cp bin test.TesteOutputStream     
```

## ✅ Saída Esperada

📤 Saída Padrão (System.out)

```bash
--- Teste Saída Padrão ---
[bytes binários das passagens]  
```

💾 Arquivo (FileOutputStream)

```bash
--- Teste Arquivo ---
Dados escritos em: passagens.dat

Conteúdo do arquivo (hex):
00 00 00 03 00 00 00 06 50 43 2D 30 30 31 00 00 ...
```

🌐 Servidor TCP

```bash
Servidor TCP ouvindo na porta 12345...
Conexão recebida de: /127.0.0.1
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
```