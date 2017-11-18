package xyz.jithin.weather.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;

import xyz.jithin.weather.model.Weather;
import xyz.jithin.weather.repository.Repo;

/**
 * Created by JIM on 18/11/17.
 */

public class WeatherVM extends ViewModel {
    public LiveData<Weather> getWeatherForecast(String location, int days) {
        MediatorLiveData<Weather> mApiResponse = new MediatorLiveData<>();
        mApiResponse.addSource(
                new Repo().getWeatherForecast(location, days),
                mApiResponse::postValue
        );
        return mApiResponse;
    }
}
