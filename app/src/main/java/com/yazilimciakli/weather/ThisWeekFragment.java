package com.yazilimciakli.weather;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.yazilimciakli.weather.Models.WeatherApi;
import com.yazilimciakli.weather.Utils.ListViewAdapter;


public class ThisWeekFragment extends Fragment {

    private WeatherApi weatherList;

    public static ThisWeekFragment newInstance(WeatherApi weather){
        ThisWeekFragment fragment = new ThisWeekFragment();
        fragment.weatherList = weather;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_this_week, container, false);

        ListView lstWeather = (ListView) view.findViewById(R.id.thisWeekWeatherList);
        ListViewAdapter adapter = new ListViewAdapter(getActivity(), weatherList.getList());
        lstWeather.setAdapter(adapter);

        return view;
    }

}
