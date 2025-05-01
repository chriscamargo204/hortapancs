package com.pancs.hortapancs;

public class DistanciaUtil {

    public static double calcularDistancia(double lat1, double lon1, double lat2, double lon2) {
        double raioTerra = 6371.0; // em quilômetros

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return raioTerra * c;
    }
}
