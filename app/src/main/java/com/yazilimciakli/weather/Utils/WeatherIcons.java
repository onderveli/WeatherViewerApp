package com.yazilimciakli.weather.Utils;

import android.content.Context;

import com.yazilimciakli.weather.R;


public class WeatherIcons {

    public static String getWeatherIcon(Context context, String iconId){
        switch(iconId) {
            case "01d" : return context.getString(R.string.wi_day_sunny);
            case "01n" : return context.getString(R.string.wi_night_clear);
            case "02d" : return context.getString(R.string.wi_day_cloudy);
            case "02n" : return context.getString(R.string.wi_night_alt_cloudy);
            case "03d" : return context.getString(R.string.wi_day_cloudy_gusts);
            case "03n" : return context.getString(R.string.wi_night_alt_cloudy_gusts);
            case "04d" :
            case "04n" : return context.getString(R.string.wi_cloudy_windy);
            case "09d" : return context.getString(R.string.wi_day_rain);
            case "09n" : return context.getString(R.string.wi_rain);
            case "10d" : return context.getString(R.string.wi_day_sunny);
            case "10n" : return context.getString(R.string.wi_night_alt_rain);
            case "11d" :
            case "11n" : return context.getString(R.string.wi_thunderstorm);
            case "13d" : return context.getString(R.string.wi_day_snow);
            case "13n" : return context.getString(R.string.wi_night_snow);
            case "50d" :
            case "50n" : return context.getString(R.string.wi_fog);
        }
        return context.getString(R.string.wi_na);
    }

}
