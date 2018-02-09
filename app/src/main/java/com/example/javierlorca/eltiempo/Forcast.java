package com.example.javierlorca.eltiempo;

/**
 * Created by Javier Lorca on 08/02/2018.
 */

public class Forcast {

    String day;
    double temp_min;
    double temp_max;
    double humidity;
    String description;
    double speed;
    double rain;
    double eve;
    double morn;

    public String getDay() {
        return day;
    }

    public double getTemp_min() {
        return temp_min;
    }

    public double getTemp_max() {
        return temp_max;
    }

    public double getHumidity() {
        return humidity;
    }

    public String getDescription() {
        return description;
    }

    public double getSpeed() {
        return speed;
    }

    public double getRain() {
        return rain;
    }

    public double getEve() {
        return eve;
    }

    public double getMorn() {
        return morn;
    }

    public Forcast(String day, double temp, double temp_min, double temp_max, double humidity, String description, double speed, double rain, double eve, double morn) {
        this.day = day;
        this.temp_max = temp_min;
        this.temp_min = temp_max;
        this.humidity = humidity;
        this.description = description;
        this.speed = speed;
        this.rain = rain;
        this.eve = eve;
        this.morn = morn;
    }

    public Forcast(){

    }

    
    public void setDay(String day) {
        this.day = day;
    }

    public void setTemp_min(double temp_min) {
        this.temp_min = temp_min;
    }

    public void setTemp_max(double temp_max) {
        this.temp_max = temp_max;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setRain(double rain) {
        this.rain = rain;
    }

    public void setEve(double eve) {
        this.eve = eve;
    }

    public void setMorn(double morn) {
        this.morn = morn;
    }
}
