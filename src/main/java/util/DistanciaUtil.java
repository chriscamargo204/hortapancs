package com.pancs.hortapancs.util;

import java.util.logging.Logger;
import java.util.Map;
import java.util.HashMap;


public class DistanciaUtil {

    private static final Logger logger = Logger.getLogger(DistanciaUtil.class.getName());
    private static final double R = 6371; // Raio da Terra em quilômetros

    // Método para calcular "distância" com base nos 5 primeiros dígitos do CEP
    public static int calcularProximidadeCep(String cep1, String cep2) {
        if (cep1 == null || cep2 == null) {
            logger.warning("CEP nulo fornecido.");
            return Integer.MAX_VALUE;
        }

        cep1 = cep1.replaceAll("[^\\d]", "");
        cep2 = cep2.replaceAll("[^\\d]", "");

        if (cep1.length() < 5 || cep2.length() < 5) {
            logger.warning("CEP inválido: " + cep1 + " ou " + cep2);
            return Integer.MAX_VALUE;
        }

        String prefixo1 = cep1.substring(0, 5);
        String prefixo2 = cep2.substring(0, 5);

        return Math.abs(Integer.parseInt(prefixo1) - Integer.parseInt(prefixo2));
    }

    // Método para calcular a distância real entre dois CEPs com base nas coordenadas (latitude/longitude)
    public static double calcularDistancia(String cep1, String cep2) {
        Map<String, Double> coord1 = obterCoordenadasDeCep(cep1);
        Map<String, Double> coord2 = obterCoordenadasDeCep(cep2);

        if (coord1 == null || coord2 == null) {
            return Double.MAX_VALUE;
        }

        double lat1 = coord1.get("latitude");
        double lon1 = coord1.get("longitude");
        double lat2 = coord2.get("latitude");
        double lon2 = coord2.get("longitude");

        // Fórmula de Haversine para calcular a distância
        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);

        double dLat = lat2 - lat1;
        double dLon = lon2 - lon1;
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(lat1) * Math.cos(lat2) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c; // Retorna a distância em quilômetros
    }

    //  (suponha que este método já foi implementado)
    public static Map<String, Double> obterCoordenadasDeCep(String cep) {
        // tem que usar API Nominatim
        Map<String, Double> coordenadas = new HashMap<>();
        coordenadas.put("latitude", -25.4298); // Exemplo de latitude fictícia
        coordenadas.put("longitude", -49.2719); // Exemplo de longitude fictícia
        return coordenadas;
    }
}
