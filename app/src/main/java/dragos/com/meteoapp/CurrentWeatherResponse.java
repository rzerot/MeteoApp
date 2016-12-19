package dragos.com.meteoapp;

import com.google.gson.annotations.SerializedName;

import dragos.com.meteoapp.Model.CurrentWeather;

/**
 * Created by L on 12/19/2016.
 */

public class CurrentWeatherResponse {
    @SerializedName("weather")
    private CurrentWeather currentWeather;

    public CurrentWeather getCurrentWeather() {
        return currentWeather;
    }

    public void setCurrentWeather(CurrentWeather currentWeather) {
        this.currentWeather = currentWeather;
    }
}
