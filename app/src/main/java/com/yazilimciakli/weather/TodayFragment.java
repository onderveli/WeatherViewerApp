package com.yazilimciakli.weather;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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

        lblIcon.setText(WeatherIcons.getWeatherIcon(getContext(), weatherApi.list.get(0).weather.get(0).icon));

        lblDegree.setText(weatherApi.list.get(0).temp.day.intValue() + "°");
        lblDegrees.setText(String.format("%s° / %s°", weatherApi.list.get(0).temp.max.intValue(), weatherApi.list.get(0).temp.min.intValue()));
        lblExplanation.setText(weatherApi.list.get(0).weather.get(0).description);
        lblCountry.setText(String.format("%s,%s", weatherApi.city.name, weatherApi.city.country));
        lblUpdate.setText("Son Güncelleme: " + new SimpleDateFormat("dd.MM.yyyy - hh:mm").format(new Timestamp(weatherApi.list.get(0).dt * 1000).getTime()));
        lblHumidity.setText(weatherApi.list.get(0).humidity.intValue() + " %");
        lblWind.setText( weatherApi.list.get(0).speed + " m/s");
        lblPressure.setText(String.format("%s hPa", weatherApi.list.get(0).pressure));

        lblHumidityIcon.setText(R.string.wi_humidity);
        lblWindIcon.setText(R.string.wi_windy);
        lblPressureIcon.setText(R.string.wi_barometer);


        return view;
    }

}
