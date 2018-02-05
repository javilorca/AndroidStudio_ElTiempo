package com.example.javierlorca.eltiempo;

/**
 * Created by Javier Lorca on 05/02/2018.
 */

public class Weather {

    double temp;
    double temp_max;
    double temp_min;
    int wind_speed;
    int wind_deg;

    public double getTemp() {
        return temp;
    }

    public double getTemp_max() {
        return temp_max;
    }

    public double getTemp_min() {
        return temp_min;
    }

    public int getWind_speed() {
        return wind_speed;
    }

    public int getWind_deg() {
        return wind_deg;
    }

    public Weather(double temp, double temp_max, double temp_min, int wind_speed, int wind_deg) {
        this.temp = temp;
        this.temp_max = temp_max;
        this.temp_min = temp_min;
        this.wind_speed = wind_speed;
        this.wind_deg = wind_deg;
    }

    public Weather(){

    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public void setTemp_max(double temp_max) {
        this.temp_max = temp_max;
    }

    public void setTemp_min(double temp_min) {
        this.temp_min = temp_min;
    }

    public void setWind_speed(int wind_speed) {
        this.wind_speed = wind_speed;
    }

    public void setWind_deg(int wind_deg) {
        this.wind_deg = wind_deg;
    }


}
