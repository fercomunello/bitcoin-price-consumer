package me.fernando.historico.bitcoin.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.fernando.historico.bitcoin.api.model.Historico;
import me.fernando.historico.bitcoin.api.model.Preco;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * Created by Fernando on 31/05/2020.
 */
public class MercadoBitcoin {

    // API do Mercado Bitcoin
    static final String API_URL = "https://www.mercadobitcoin.net/api/BTC";

    // Mercado Bitcoin abriu em 06/12/2013
    static final String DATA_INICIAL = "2013-12-06";

    // Atributo "User-Agent", adicionado no header da request
    static final String USER_AGENT = "Chrome/83.0.4103.61";

    // Caminho do arquivo JSON
    static final String JSON_FILE_PATH = "cotacao.json";

    // Objeto que será convertido em Json
    private final Historico historico;

    // API do Google para converter Json
    private final Gson gson;

    public MercadoBitcoin() {
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        this.historico = carregarHistorico();
    }

    public Historico carregarHistorico() {
        Historico Historico = new Historico(new ArrayList<>());

        try {
            File file = new File(JSON_FILE_PATH);

            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            byte[] bytes = Files.readAllBytes(Paths.get(JSON_FILE_PATH));

            if (bytes.length > 0) {
                Historico = this.gson.fromJson(new String(bytes), Historico.class);

                Historico.getPrecos().forEach(preco -> preco.setJson(this.gson.toJson(preco)));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return Historico;
    }

    public void gravarHistorico() {
        Preco ultimoPreco = null;

        int jsonSize = this.historico.getPrecos().size();

        if (jsonSize > 0) {
            ultimoPreco = this.historico.getPrecos().get(jsonSize - 1);
        }

        LocalDate dataInicial = LocalDate.parse(DATA_INICIAL);
        LocalDate dataFinal = LocalDate.now().minusDays(1);

        if (ultimoPreco != null) {
            LocalDate dataUltimoPreco = LocalDate.parse(ultimoPreco.getData());

            if (dataUltimoPreco != null) {
                dataInicial = dataUltimoPreco.plusDays(1);
            }
        }

        Stream<LocalDate> dataStream = Stream.iterate(dataInicial, localDate -> localDate.plusDays(1))
                .limit(ChronoUnit.DAYS.between(dataInicial, dataFinal) + 1);

        this.historico.getPrecos().stream().map(Preco::getJson).forEach(System.out::println);

        dataStream.forEachOrdered(data -> {
            int dia = data.get(ChronoField.DAY_OF_MONTH);
            int mes = data.get(ChronoField.MONTH_OF_YEAR);
            int ano = data.get(ChronoField.YEAR);

            Preco preco = buscarPreco(ano, mes, dia);

            System.out.println(this.gson.toJson(preco) + "\n");

            this.historico.getPrecos().add(preco);

            atualizarJson();
        });
    }

    public Preco buscarPreco(int ano, int mes, int dia) {
        HttpURLConnection connection = null;

        Preco preco = null;

        try {
            URL url = new URL(API_URL + queryPreco(ano, mes, dia));

            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("User-Agent", USER_AGENT);
            connection.setConnectTimeout(4000);
            connection.setReadTimeout(4000);
            connection.setDoOutput(true);

            int statusCode = connection.getResponseCode();

            if (statusCode == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                String line;

                StringBuilder response = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                preco = this.gson.fromJson(response.toString(), Preco.class);
                preco.setJson(this.gson.toJson(preco));

                reader.close();
            } else {
                System.err.println("Não foi possível obter os dados. HTTP Status: " + statusCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return preco;
    }

    private String queryPreco(int ano, int mes, int dia) {
        String query = "/day-summary/{ano}/{mes}/{dia}";

        return query.replace("{ano}", String.valueOf(ano))
                .replace("{mes}", String.valueOf(mes))
                .replace("{dia}", String.valueOf(dia));
    }

    private synchronized void atualizarJson() {
        try {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    byte[] jsonBytes = gson.toJson(historico).getBytes();

                    try {
                        Files.write(Paths.get(JSON_FILE_PATH), jsonBytes);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            thread.start();
            thread.join();

            //Thread.sleep(1000L);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
