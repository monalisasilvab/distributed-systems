
---

# Questão 4: Comunicação Cliente-Servidor com Serialização Manual  
---

## 📌 Sobre o Projeto  
Implementação de um serviço remoto usando comunicação cliente-servidor via sockets TCP em Java. A troca de dados entre cliente e servidor é realizada com **serialização manual de objetos** (sem uso de `ObjectOutputStream` ou `ObjectInputStream`), conforme requisitos da **Questão 4** do trabalho.

---

## ✅ Funcionalidades  
- **Serialização Manual:**  
  Conversão explícita de objetos (`Usuario`) para bytes e vice-versa usando `DataOutputStream` e `DataInputStream`.  
- **Comunicação TCP:**  
  Cliente envia uma requisição contendo um objeto serializado, e o servidor responde com o mesmo objeto ecoado.  
- **Empacotamento/Desempacotamento:**  
  Ambos os lados realizam o processo de serialização/desserialização manualmente.  

---

## 🧰 Tecnologias  
- Java (JDK 8+)  
- Sockets TCP  
- Streams: `DataOutputStream`, `DataInputStream`  

---

## ▶️ Como Executar  
### 1. Instale o JDK (Linux)  
```bash
sudo apt update  
sudo apt install default-jdk  
```

### 2. Compile os Arquivos  
```bash
javac Usuario.java ServidorTCP.java ClienteTCP.java  
```

### 3. Execute o Servidor  
```bash
java ServidorTCP  
```
A saída será:  
```
Servidor TCP rodando...  
Recebido: Usuario{id=1, nome='João', email='joao@example.com'}  
```

### 4. Execute o Cliente  
Em outro terminal:  
```bash
java ClienteTCP  
```
Exemplo de interação:  
```
Digite seu nome: João  
Digite seu email: joao@example.com  
Resposta do servidor: Usuario{id=1, nome='João', email='joao@example.com'}  
```

---

## 📋 Requisitos Cumpridos  
| Requisito | Detalhe |  
|----------|---------|  
| **Comunicação via Sockets TCP** | Usado `ServerSocket` e `Socket` para troca de mensagens. |  
| **Serialização Manual** | Objetos são convertidos em bytes com `DataOutputStream` e reconstruídos com `DataInputStream`. |  
| **Empacotamento/Desempacotamento** | Cliente empacota a requisição antes de enviar; servidor desempacota, processa e empacota a resposta. |  
| **Sem `ObjectOutputStream`** | O código usa apenas streams de baixo nível (`DataOutputStream`/`DataInputStream`). |  

---
