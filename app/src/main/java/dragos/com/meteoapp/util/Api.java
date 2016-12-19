package dragos.com.meteoapp.util;

import dragos.com.meteoapp.CurrentWeatherResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by L on 12/18/2016.
 */

public interface Api {
    static final String METEOKEY = "appid=279ac6d3253d468ca7484068d24097e2";
//    @Get("weather?q={city name},{country code}"+)

    //http://api.openweathermap.org/data/2.5/weather?q=iasi,ro&appid=279ac6d3253d468ca7484068d24097e2
    @GET("weather?" + METEOKEY)
    Call<CurrentWeatherResponse> getCurrentWeather(@Query("q") String name);

}
