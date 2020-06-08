package me.fernando.historico.bitcoin.api.model;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

/**
 * Created by Fernando on 31/05/2020.
 */
public class Preco {

    @SerializedName("date")
    private String data;

    @SerializedName("opening")
    private BigDecimal valorAbertura;

    @SerializedName("closing")
    private BigDecimal valorFechado;

    @SerializedName("lowest")
    private BigDecimal valorMin;

    @SerializedName("highest")
    private BigDecimal valorMax;

    @SerializedName("volume")
    private BigDecimal volume;

    @SerializedName("quantity")
    private BigDecimal quantidade;

    @SerializedName("amount")
    private int montante;

    @SerializedName("avg_price")
    private BigDecimal valorMedio;

    private transient String json;

    public Preco() {
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public BigDecimal getValorAbertura() {
        return valorAbertura;
    }

    public void setValorAbertura(BigDecimal valorAbertura) {
        this.valorAbertura = valorAbertura;
    }

    public BigDecimal getValorFechado() {
        return valorFechado;
    }

    public void setValorFechado(BigDecimal valorFechado) {
        this.valorFechado = valorFechado;
    }

    public BigDecimal getValorMin() {
        return valorMin;
    }

    public void setValorMin(BigDecimal valorMin) {
        this.valorMin = valorMin;
    }

    public BigDecimal getValorMax() {
        return valorMax;
    }

    public void setValorMax(BigDecimal valorMax) {
        this.valorMax = valorMax;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }

    public int getMontante() {
        return montante;
    }

    public void setMontante(int montante) {
        this.montante = montante;
    }

    public BigDecimal getValorMedio() {
        return valorMedio;
    }

    public void setValorMedio(BigDecimal valorMedio) {
        this.valorMedio = valorMedio;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
}