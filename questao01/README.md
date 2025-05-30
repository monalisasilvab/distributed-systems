# Projeto: Sistema de Passagens Aéreas

Este projeto simula a gestão de passagens aéreas em diferentes classes (primeira classe e classe econômica), com a possibilidade de transferência de passagens entre proprietários. O sistema é baseado em um modelo orientado a objetos com uso de classes POJO, interfaces e serviços.

## 📌 Requisitos da Atividade

Cada estudante deve:
- Definir um **serviço remoto** (ex: venda de passagens, check-in, gerenciamento de voos).
- Criar **classes do tipo POJO** que representem informações relevantes do serviço escolhido.
- Implementar **pelo menos 2 classes de modelo** que simulem serviços (reais ou fictícios).

## 📁 Estrutura do Projeto

    projeto-passagens-aereas/
    ├── src/
    │ ├── interfaces/ # Interfaces do sistema
    │ ├── model/ # Classes de modelo (POJOs)
    │ ├── services/ # Implementações de serviços
    │ └── SistemaAereoApp.java # Classe principal
    ├── bin/ # Diretório de saída da compilação


## 🛠️ Compilação

Navegue até a raiz do projeto e compile os arquivos Java:

```bash
cd questao01
javac -d bin src/*.java src/interfaces/*.java src/model/*.java src/services/*.java
```

## ▶️ Execução

```bash
java -cp bin SistemaAereoApp
```

## ✅ Saída esperada

Passagem PC-001 transferida para: Carlos Mendes

Passagens no voo V101:  
• PC-001 (PassagemPrimeiraClasse) - Proprietário: Carlos Mendes  
• EC-205 (PassagemClasseEconomica) - Proprietário: Maria Oliveira  
