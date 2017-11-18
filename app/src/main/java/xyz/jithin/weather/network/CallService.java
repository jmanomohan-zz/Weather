package xyz.jithin.weather.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import xyz.jithin.weather.BuildConfig;
import xyz.jithin.weather.model.Weather;

/**
 * Created by JIM on 18/11/17.
 */

public interface CallService {

    @GET("forecast.json?key=" + BuildConfig.APIXU_API)
    Call<Weather> getWeatherForecast(@Query("q") String location, @Query("days") int days);

}
