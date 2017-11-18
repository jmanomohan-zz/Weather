package xyz.jithin.weather.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.jithin.weather.model.Weather;
import xyz.jithin.weather.network.Connector;

/**
 * Created by JIM on 18/11/17.
 */

public class Repo implements RepoService {
    @Override
    public LiveData<Weather> getWeatherForecast(String location, int days) {
        final MutableLiveData<Weather> liveData = new MutableLiveData<>();
        Call<Weather> call = Connector.load().call().getWeatherForecast(location, days);
        call.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                liveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                liveData.setValue(null);
            }
        });
        return liveData;
    }
}
