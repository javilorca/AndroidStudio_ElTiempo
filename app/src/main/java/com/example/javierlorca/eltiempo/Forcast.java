package com.example.javierlorca.eltiempo;

import java.util.Date;

/**
 * Created by Javier Lorca on 08/02/2018.
 */

public class Forcast {

    double day;
    double temp;
    double temp_min;
    double temp_max;
    double humidity;
    String description;
    double speedprev;
    double rain;
    double eve;
    double morn;
    Date dt_txt;
    Double deg;

    public Date getDt_txt() {
        return dt_txt;
    }

    public void setDt_txt(Date dt_txt) {
        this.dt_txt = dt_txt;
    }

    public Double getDeg() {
        return deg;
    }

    public void setDeg(Double deg) {
        this.deg = deg;
    }

    public double getDay() {
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

    public double getSpeedprev() {
        return speedprev;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getRain() {

        return rain;
    }

    public double getTemp() {
        return temp;
    }

    public double getEve() {

        return eve;
    }

    public double getMorn() {
        return morn;
    }

    public Forcast(double day, double temp, double temp_min, double temp_max, double humidity, String description, double speedprev, double rain, double eve, double morn) {
        this.day = day;
        this.temp_max = temp_min;
        this.temp_min = temp_max;
        this.humidity = humidity;
        this.description = description;
        this.speedprev = speedprev;
        this.rain = rain;
        this.eve = eve;
        this.morn = morn;
    }

    public Forcast(){

    }

    
    public void setDay(double day) {
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

    public void setSpeedprev(double speedprev) {
        this.speedprev = speedprev;
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

    public double toCelsius(double kelvin){

        return kelvin-273.15;
    }

}
