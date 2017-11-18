package xyz.jithin.weather.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import xyz.jithin.weather.R;
import xyz.jithin.weather.adapter.ForecastAdapter;
import xyz.jithin.weather.model.Weather;
import xyz.jithin.weather.viewmodel.WeatherVM;

/**
 * Created by JIM on 18/11/17.
 */

public class WeatherFragment extends Fragment implements View.OnClickListener {

    String DEGREE;

    public WeatherFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weather, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        DEGREE = getString(R.string.degree);
        if (getView() == null) return;
        getView().findViewById(R.id.button_for_retry).setOnClickListener(this);
        getView().findViewById(R.id.forecast).setVisibility(View.INVISIBLE);
        ViewCompat.animate(getView().findViewById(R.id.forecast)).translationYBy(1000).start();
        fetchWeatherForecast();
    }

    public void fetchWeatherForecast() {
        retry(false);
        progress(true);
        WeatherVM weatherVM = ViewModelProviders.of(getActivity()).get(WeatherVM.class);
        weatherVM.getWeatherForecast("bengaluru", 5).observe(getActivity(), apiResponse -> {
            progress(false);
            if (getView() == null || apiResponse == null) {
                retry(true);
                return;
            }

            if (apiResponse.getCurrent() != null) {
                String temp = ((int) apiResponse.getCurrent().getTemp_c()) + DEGREE;
                ((TextView) getView().findViewById(R.id.text_current_degree)).setText(temp);
            }

            if (apiResponse.getLocation() != null) {
                ((TextView) getView().findViewById(R.id.text_current_location)).setText(apiResponse.getLocation().getName());
            }

            if (apiResponse.getForecast() != null && apiResponse.getForecast().getForecastday().size() > 0) {
                populateForecast(apiResponse.getForecast().getForecastday());
            }
        });
    }

    private void populateForecast(List<Weather.Forecast.ForecastDay> list) {
        if (getView() == null) return;

        if (list.size() > 0) list.remove(0);//removing today's info from forecast list

        RecyclerView recycler = getView().findViewById(R.id.recycler_forecast);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        ForecastAdapter adapter = new ForecastAdapter();
        adapter.setList(list);
        recycler.setAdapter(adapter);

        getView().findViewById(R.id.forecast).setVisibility(View.VISIBLE);
        ViewCompat.animate(getView().findViewById(R.id.forecast)).translationYBy(-1000).setDuration(600).start();
    }

    private void progress(boolean show) {
        if (getView() != null)
            getView().findViewById(R.id.progress).setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private void retry(boolean show) {
        if (getView() != null)
            getView().findViewById(R.id.retry).setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_for_retry:
                fetchWeatherForecast();
                break;
        }
    }
}
