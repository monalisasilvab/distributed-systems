package model;

import java.util.ArrayList;
import java.util.List;

public class Voo {
    private String numeroVoo;
    private String origem;
    private String destino;
    private List<Passagem> passagens = new ArrayList<>();

    public Voo(String numeroVoo, String origem, String destino) {
        this.numeroVoo = numeroVoo;
        this.origem = origem;
        this.destino = destino;
    }

    public void adicionarPassagem(Passagem passagem) {
        passagens.add(passagem);
    }

    public void removerPassagem(Passagem passagem) {
        passagens.remove(passagem);
    }

    public List<Passagem> getPassagens() {
        return new ArrayList<>(passagens);
    }

    public String getNumeroVoo() { return numeroVoo; }
    public String getOrigem() { return origem; }
    public String getDestino() { return destino; }
}