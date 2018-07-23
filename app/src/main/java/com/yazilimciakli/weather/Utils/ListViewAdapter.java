package com.yazilimciakli.weather.Utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yazilimciakli.weather.Models.List;
import com.yazilimciakli.weather.R;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;

public class ListViewAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private java.util.List<List> weatherList;

    public ListViewAdapter(Activity activity, List[] list) {
        mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.weatherList = Arrays.asList(list);
    }

    @Override
    public int getCount() {
        return weatherList.size();
    }

    @Override
    public com.yazilimciakli.weather.Models.List getItem(int position) {
        return weatherList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = mInflater.inflate(R.layout.listview_item, null);


        TextView lblIcon = (TextView) view.findViewById(R.id.lblIcon);
        TextView lblDay = (TextView) view.findViewById(R.id.lblDay);
        TextView lblExplanation = (TextView) view.findViewById(R.id.lblExplanation);
        TextView lblMax = (TextView) view.findViewById(R.id.lblMax);
        TextView lblMin = (TextView) view.findViewById(R.id.lblMin);
        Typeface weatherFont = Typeface.createFromAsset(view.getContext().getAssets(), "fonts/weather.ttf");
        lblIcon.setTypeface(weatherFont);

        com.yazilimciakli.weather.Models.List weatherItem = weatherList.get(position);

        lblIcon.setText(WeatherIcons.getWeatherIcon(view.getContext(), weatherItem.getWeather()[0].icon));
        lblDay.setText(new SimpleDateFormat("d MMMM, EEEE").format(new Timestamp(Long.valueOf(weatherItem.getDt()) * 1000).getTime()));
        lblExplanation.setText(weatherItem.getWeather()[0].description);
        lblMax.setText(weatherItem.getMain().getTemp_max() + "°");
        lblMin.setText(weatherItem.getMain().getTemp_min()  + "°");
        return view;
    }
}