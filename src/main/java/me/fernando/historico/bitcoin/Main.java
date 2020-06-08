package me.fernando.historico.bitcoin;

import me.fernando.historico.bitcoin.api.MercadoBitcoin;

/**
 * Created by Fernando on 31/05/2020.
 */
public class Main {

    public static void main(String[] args) {

        MercadoBitcoin api = new MercadoBitcoin();

        api.gravarHistorico();

    }
}
