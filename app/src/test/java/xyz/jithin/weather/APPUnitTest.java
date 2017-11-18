package xyz.jithin.weather;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.jithin.weather.model.Weather;
import xyz.jithin.weather.network.Connector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class APPUnitTest {

    Response<Weather> mResponse;
    CountDownLatch lock = new CountDownLatch(1);

    @Test
    public void checkWeatherForecastAPIWorking() throws InterruptedException {
        Call<Weather> weatherCall = Connector.load().call().getWeatherForecast("bengaluru", 5);
        weatherCall.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                mResponse = response;
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {

            }
        });

        lock.await(10 * 1000, TimeUnit.MILLISECONDS);

        assertEquals(mResponse.code(), 200);

        Weather weather = mResponse.body();
        assertNotNull(weather);
        assertNotNull(weather.getCurrent());
        assertNotNull(weather.getLocation());
        assertNotNull(weather.getForecast());
        assertEquals(weather.getForecast().getForecastday().size(), 5);

        System.out.println("Current Temp:" + weather.getCurrent().getTemp_c());
        System.out.println("Day1 Temp   :" + weather.getForecast().getForecastday().get(1).getDay().getAvgtemp_c());
        System.out.println("Day2 Temp   :" + weather.getForecast().getForecastday().get(2).getDay().getAvgtemp_c());
        System.out.println("Day3 Temp   :" + weather.getForecast().getForecastday().get(3).getDay().getAvgtemp_c());
        System.out.println("Day4 Temp   :" + weather.getForecast().getForecastday().get(4).getDay().getAvgtemp_c());
    }
}