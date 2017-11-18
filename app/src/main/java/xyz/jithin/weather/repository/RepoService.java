package xyz.jithin.weather.repository;

import android.arch.lifecycle.LiveData;

import xyz.jithin.weather.model.Weather;

/**
 * Created by JIM on 18/11/17.
 */

public interface RepoService {
    LiveData<Weather> getWeatherForecast(String location, int days);
}
