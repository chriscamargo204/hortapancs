// usar somente com API nominatim
package com.pancs.hortapancs.model;

public class Coordenada {
    private double latitude;
    private double longitude;

    // Construtores
    public Coordenada() {
    }

    public Coordenada(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getters e Setters
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }


    @Override
    public String toString() {
        return "Coordenada{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
