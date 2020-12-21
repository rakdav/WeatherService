package com.example.weatherservice;

public class Meteo {
    private String date;
    private String tod;
    private String pressure;
    private String temp;
    private String humidity;
    private String wind;
    private String cloud;
    private String feel;

    public Meteo(String date, String tod, String pressure, String temp, String humidity, String wind, String cloud) {
        this.date = date;
        this.tod = tod;
        this.pressure = pressure;
        this.temp = temp;
        this.humidity = humidity;
        this.wind = wind;
        this.cloud = cloud;
    }

    public String getFeel() {
        return feel;
    }

    public void setFeel(String feel) {
        this.feel = feel;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTod() {
        return tod;
    }

    public void setTod(String tod) {
        this.tod = tod;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getCloud() {
        return cloud;
    }

    public void setCloud(String cloud) {
        this.cloud = cloud;
    }
}
