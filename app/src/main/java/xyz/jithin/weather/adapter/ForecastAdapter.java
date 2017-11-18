package xyz.jithin.weather.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import xyz.jithin.weather.R;
import xyz.jithin.weather.model.Weather;
import xyz.jithin.weather.util.Util;

/**
 * Created by JIM on 18/11/17.
 */

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ViewHolder> {

    List<Weather.Forecast.ForecastDay> list;

    public void setList(List<Weather.Forecast.ForecastDay> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_forecast, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Weather.Forecast.ForecastDay forecastDay = list.get(position);
        String day = Util.getWeek(forecastDay.getDate());
        holder.week.setText(day);

        String temp = ((int) forecastDay.getDay().getAvgtemp_c()) + " C";
        holder.temp.setText(temp);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView week, temp;

        public ViewHolder(View itemView) {
            super(itemView);
            week = (TextView) itemView.findViewById(R.id.text_week);
            temp = (TextView) itemView.findViewById(R.id.text_temp);
        }
    }
}
