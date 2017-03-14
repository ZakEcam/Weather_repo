package com.example.a13058.weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by 13058 on 14-02-17.
 */

public class details {
    private static String city_name = null;

    private java.sql.Date day_stamp = null;
    private double degree_day = 0;
    private double degree_eve = 0;
    private double degree_morn = 0;
    private double degree_night = 0;
    private double day_pressure = 0;
    private double day_humidity = 0;
    private String day_weather_description = null;
    private double day_wind_speed = 0;


    private static ArrayList<details> det = new ArrayList<>();


    public static void parse(String json) throws JSONException {
        det.clear();

        JSONObject jsonWeathers = new JSONObject(json);

        JSONObject jsonCity = jsonWeathers.getJSONObject("city");

        city_name = jsonCity.getString("name");

        JSONArray jsonList = jsonWeathers.getJSONArray("list");

        for (int i=0; i<jsonList.length(); i++) {
            JSONObject jsonWeather = jsonList.getJSONObject(i);
            JSONObject jsonWeatherInfo = jsonWeather.getJSONObject("temp");

            java.sql.Date day_stamp = new java.sql.Date(jsonWeather.getInt("dt"));
            double degree_day = jsonWeatherInfo.getDouble("day");
            double degree_eve = jsonWeatherInfo.getDouble("eve");
            double degree_morn = jsonWeatherInfo.getDouble("morn");
            double degree_night = jsonWeatherInfo.getDouble("night");

            double day_pressure = jsonWeather.getDouble("pressure");
            double day_humidity = jsonWeather.getDouble("humidity");
            String day_weather_description = jsonWeather.getJSONArray("weather").getJSONObject(0).getString("description");
            double day_wind_speed = jsonWeather.getDouble("speed");


            det.add(new details(day_stamp, degree_day, degree_eve, degree_morn, degree_night, day_pressure, day_humidity, day_weather_description, day_wind_speed));
        }
    }

    details(java.sql.Date day_stamp, double degree_day, double degree_eve, double degree_morn, double degree_night, double day_pressure, double day_humidity, String day_weather_description, double day_wind_speed){
        this.day_stamp = day_stamp;
        this.degree_day = degree_day;
        this.degree_eve = degree_eve;
        this.degree_morn = degree_morn;
        this.degree_night = degree_night;
        this.day_pressure = day_pressure;
        this.day_humidity = day_humidity;
        this.day_weather_description = day_weather_description;
        this.day_wind_speed = day_wind_speed;
    }

    public static details find(int index) {

        return det.get(index);
    }

    public static ArrayList<details> getWeathersList(){

        return det;
    }

    public Double getDegree_day(){

        return (double) Math.round(degree_day*100)/100;
    }

    public Double getDegree_eve(){

        return (double) Math.round(degree_eve*100)/100;
    }

    public Double getDegree_morn(){

        return (double) Math.round(degree_morn*100)/100;
    }

    public Double getDegree_night(){

        return (double) Math.round(degree_night*100)/100;
    }

    public Double getDay_pressure(){

        return (double) Math.round(day_pressure*100)/100;
    }

    public Double getDay_humidity(){

        return (double) Math.round(day_humidity*100)/100;
    }

    public String getDay_weather_description(){

        return day_weather_description;
    }

    public Double getDay_wind_speed(){

        return (double) Math.round(day_wind_speed*100)/100;
    }
}
