package dragos.com.meteoapp;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import dragos.com.meteoapp.Model.CurrentWeather;

/**
 * Created by L on 12/19/2016.
 */

public class CurrentWeatherResponse {
    @SerializedName("weather")
    private ArrayList<CurrentWeather> currentWeather;

    public ArrayList<CurrentWeather> getCurrentWeather() {
        return currentWeather;
    }

    public void setCurrentWeather(ArrayList<CurrentWeather> currentWeather) {
        this.currentWeather = currentWeather;
    }
}
