package com.alura.screenmatch.principal;

import com.alura.screenmatch.modelos.FilteredRates;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class PrincipalConBusqueda {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner lectura = new Scanner(System.in);
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .setPrettyPrinting()
                .create();

        var apiKey = "198119df9d5008f5dc67a304";

        String direccion = "https://v6.exchangerate-api.com/v6/" + apiKey + "/latest/USD";

        FilteredRates filteredRates = null;

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(direccion))
                    .build();
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());

            String json = response.body();
            //System.out.println(json);


            JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
            JsonObject conversionRates = jsonObject.getAsJsonObject("conversion_rates");

            filteredRates = new FilteredRates();
            filteredRates.setUSD(conversionRates.get("USD").getAsDouble());
            filteredRates.setARS(conversionRates.get("ARS").getAsDouble());
            filteredRates.setBOB(conversionRates.get("BOB").getAsDouble());
            filteredRates.setBRL(conversionRates.get("BRL").getAsDouble());
            filteredRates.setCLP(conversionRates.get("CLP").getAsDouble());
            filteredRates.setCOP(conversionRates.get("COP").getAsDouble());

            //System.out.println("Tasas de cambio filtradas: " + filteredRates);


            FileWriter escritura = new FileWriter("filtered_exchange_rates.json");
            escritura.write(gson.toJson(filteredRates));
            escritura.close();

        } catch (IOException | InterruptedException e) {
            System.out.println("Ocurrió un error: ");
            System.out.println(e.getMessage());
        }

        if (filteredRates != null) {
            while (true) {
                System.out.println("Menú de Conversión de Monedas:");
                System.out.println("1. Convertir Moneda");
                System.out.println("2. Salir");
                System.out.print("Seleccione una opción: ");

                String opcion = lectura.nextLine();

                if (opcion.equals("2")) {
                    break;
                } else if (opcion.equals("1")) {
                    System.out.print("Ingrese la cantidad que desea convertir: ");
                    double cantidad = Double.parseDouble(lectura.nextLine());

                    System.out.print("Seleccione la moneda de origen (USD, ARS, BOB, BRL, CLP, COP): ");
                    String monedaOrigen = lectura.nextLine().toUpperCase();

                    System.out.print("Seleccione la moneda de destino (USD, ARS, BOB, BRL, CLP, COP): ");
                    String monedaDestino = lectura.nextLine().toUpperCase();

                    double resultado = convertirMoneda(filteredRates, cantidad, monedaOrigen, monedaDestino);
                    System.out.printf("Resultado: %.2f %s%n", resultado, monedaDestino);
                } else {
                    System.out.println("Opción no válida. Intente nuevamente.");
                }
            }
        }

        System.out.println("Finalizó la ejecución del programa!");
    }

    private static double convertirMoneda(FilteredRates rates, double cantidad, String origen, String destino) {
        double tasaOrigen = obtenerTasa(rates, origen);
        double tasaDestino = obtenerTasa(rates, destino);

        if (tasaOrigen == 0 || tasaDestino == 0) {
            throw new IllegalArgumentException("Moneda no válida");
        }


        double cantidadEnUSD = cantidad / tasaOrigen;


        return cantidadEnUSD * tasaDestino;
    }

    private static double obtenerTasa(FilteredRates rates, String moneda) {
        switch (moneda) {
            case "USD": return rates.getUSD();
            case "ARS": return rates.getARS();
            case "BOB": return rates.getBOB();
            case "BRL": return rates.getBRL();
            case "CLP": return rates.getCLP();
            case "COP": return rates.getCOP();
            default: return 0;
        }
    }
}


