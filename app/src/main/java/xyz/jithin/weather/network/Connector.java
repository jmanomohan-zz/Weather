package xyz.jithin.weather.network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

/**
 * Created by JIM on 18/11/17.
 */

public class Connector {
    private static Connector retrofitOBJ = null;
    private Retrofit retrofit = null;
    private CallService service = null;

    public static Connector load() {
        if (retrofitOBJ == null)
            retrofitOBJ = new Connector();
        return retrofitOBJ;
    }

    public Connector() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://api.apixu.com/v1/")
                .client(getOkHttpClient())
                .addConverterFactory(MoshiConverterFactory.create())
                .build();
        service = retrofit.create(CallService.class);
    }

    private OkHttpClient getOkHttpClient() {
        return new OkHttpClient.Builder().build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public CallService call() {
        return service;
    }
}
