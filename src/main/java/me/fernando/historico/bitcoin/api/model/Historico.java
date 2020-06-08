package me.fernando.historico.bitcoin.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Fernando on 31/05/2020.
 */
public class Historico {

    @SerializedName("bitcoinBRL")
    private List<Preco> precos;

    public Historico(List<Preco> precos) {
        this.precos = precos;
    }

    public List<Preco> getPrecos() {
        return precos;
    }

    public void setPrecos(List<Preco> precos) {
        this.precos = precos;
    }
}
