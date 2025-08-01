import requests
import json

BASE_URL = "http://172.25.206.81:8080/api/passagens"

def reservar_primeira(numero_voo, codigo, proprietario, preco, taxa_embarque=50.0, taxa_alimentacao=30.0):
    """Reserva uma passagem de primeira classe com todos os parâmetros necessários"""
    payload = {
        "numeroVoo": numero_voo,
        "codigo": codigo,
        "proprietario": proprietario,
        "preco": preco,
        "taxaEmbarque": taxa_embarque,
        "taxaAlimentacao": taxa_alimentacao
    }
    print(f"Enviando para primeira classe: {json.dumps(payload, indent=2)}")
    response = requests.post(f"{BASE_URL}/primeira", json=payload)
    
    if response.status_code != 200:
        print(f"Erro {response.status_code}: {response.text}")
        return {"status": response.status_code, "error": response.text}
    
    try:
        return response.json()
    except ValueError:
        return {"status": response.status_code, "message": response.text}

def reservar_economica(numero_voo, codigo, proprietario, preco, assento):
    """Reserva uma passagem de classe econômica"""
    payload = {
        "numeroVoo": numero_voo,
        "codigo": codigo,
        "proprietario": proprietario,
        "preco": preco,
        "assento": assento
    }
    print(f"Enviando para classe econômica: {json.dumps(payload, indent=2)}")
    response = requests.post(f"{BASE_URL}/economica", json=payload)
    
    if response.status_code != 200:
        print(f"Erro {response.status_code}: {response.text}")
        return {"status": response.status_code, "error": response.text}
    
    try:
        return response.json()
    except ValueError:
        return {"status": response.status_code, "message": response.text}

def cancelar(numero_voo, codigo):
    """Cancela uma passagem usando query parameters"""
    # Constrói a URL com os parâmetros de query
    url = f"{BASE_URL}?numeroVoo={numero_voo}&codigo={codigo}"
    response = requests.delete(url)
    
    if response.status_code != 204:
        print(f"Erro ao cancelar: {response.status_code} - {response.text}")
    
    return response.status_code == 204

def transferir(numero_voo, codigo, novo_proprietario):
    """Transfere proprietário usando PUT (método correto)"""
    # Estrutura RESTful: PUT /passagens/{codigo}/transferir
    # OU: PUT /passagens/transferir com o corpo adequado
    payload = {
        "numeroVoo": numero_voo,
        "codigo": codigo,
        "novoProprietario": novo_proprietario
    }
    # Alterado de POST para PUT
    response = requests.put(f"{BASE_URL}/transferir", json=payload)
    
    if response.status_code != 200:
        print(f"Erro na transferência: {response.status_code} - {response.text}")
    
    return response.status_code == 200

# Exemplo de uso com os voos que existem no backend (V100 e V200)
if __name__ == "__main__":
    print("=== Reservando Primeira Classe ===")
    res1 = reservar_primeira("V100", "P001", "Joao", 1200.50)
    print("Resposta:", json.dumps(res1, indent=2))
    
    print("\n=== Reservando Classe Econômica ===")
    res2 = reservar_economica("V200", "E001", "Maria", 550.75, 12)
    print("Resposta:", json.dumps(res2, indent=2))
    
    print("\n=== Transferindo Proprietário ===")
    sucesso = transferir("V100", "P001", "Carlos")
    print("Transferência bem-sucedida?", sucesso)
    
    print("\n=== Cancelando Passagem ===")
    cancelado = cancelar("V200", "E001")
    print("Cancelamento bem-sucedido?", cancelado)
