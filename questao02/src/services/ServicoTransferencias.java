package services;

import model.Passagem;

public class ServicoTransferencias {
    public void transferirPassagem(Passagem passagem, String novoProprietario) {
        if (passagem != null) {
            passagem.transferir(novoProprietario);
        } else {
            throw new IllegalArgumentException("Passagem inválida");
        }
    }
}