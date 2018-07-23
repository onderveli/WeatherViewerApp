package com.yazilimciakli.weather;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yazilimciakli.weather.Models.WeatherApi;
import com.yazilimciakli.weather.Utils.WeatherIcons;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;


public class TodayFragment extends Fragment {

    private WeatherApi weatherApi;

    TextView lblDegree;
    TextView lblIcon;
    TextView lblDegrees;
    TextView lblCountry;
    TextView lblUpdate;
    TextView lblExplanation;
    TextView lblHumidity;
    TextView lblHumidityIcon;
    TextView lblWind;
    TextView lblWindIcon;
    TextView lblPressure;
    TextView lblPressureIcon;


    public static TodayFragment newInstance(WeatherApi api){
        TodayFragment fragment = new TodayFragment();
        fragment.weatherApi = api;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_today, container, false);

        lblDegree = (TextView) view.findViewById(R.id.lblDegree);
        lblIcon = (TextView) view.findViewById(R.id.lblIcon);
        lblDegrees = (TextView) view.findViewById(R.id.lblDegrees);
        lblCountry = (TextView) view.findViewById(R.id.lblCountry);
        lblUpdate = (TextView) view.findViewById(R.id.lblUpdate);
        lblExplanation = (TextView) view.findViewById(R.id.lblExplanation);
        lblHumidity = (TextView) view.findViewById(R.id.lblHumidity);
        lblHumidityIcon = (TextView) view.findViewById(R.id.lblHumidityIcon);
        lblWind = (TextView) view.findViewById(R.id.lblWind);
        lblWindIcon = (TextView) view.findViewById(R.id.lblWindIcon);
        lblPressure = (TextView) view.findViewById(R.id.lblPressure);
        lblPressureIcon = (TextView) view.findViewById(R.id.lblPressureIcon);


        Typeface weatherFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/weather.ttf");
        lblIcon.setTypeface(weatherFont);
        lblHumidityIcon.setTypeface(weatherFont);
        lblWindIcon.setTypeface(weatherFont);
        lblPressureIcon.setTypeface(weatherFont);

        lblIcon.setText(WeatherIcons.getWeatherIcon(getContext(), weatherApi.getList()[0].getWeather()[0].icon));

        lblDegree.setText(weatherApi.getList()[0].getMain().getTemp()+ "°");
        lblDegrees.setText(String.format("%s° / %s°", weatherApi.getList()[0].getMain().getTemp_max(), weatherApi.getList()[0].getMain().getTemp_min()));
        lblExplanation.setText(weatherApi.getList()[0].getWeather()[0].description);
                lblCountry.setText(String.format("%s,%s", weatherApi.getCity().getName(), weatherApi.getCity().getCountry()));
        lblUpdate.setText("Son Güncelleme: " + new SimpleDateFormat("dd.MM.yyyy - hh:mm").format(new Timestamp(Long.valueOf(weatherApi.getList()[0].getDt()) * 1000).getTime()));
        lblHumidity.setText(Integer.valueOf(weatherApi.getList()[0].getMain().getHumidity()) + " %");
        lblWind.setText( weatherApi.getList()[0].getWind().getSpeed() + " m/s");
        lblPressure.setText(String.format("%s hPa", weatherApi.getList()[0].getMain().getPressure()));

        lblHumidityIcon.setText(R.string.wi_humidity);
        lblWindIcon.setText(R.string.wi_windy);
        lblPressureIcon.setText(R.string.wi_barometer);


        return view;
    }

}
