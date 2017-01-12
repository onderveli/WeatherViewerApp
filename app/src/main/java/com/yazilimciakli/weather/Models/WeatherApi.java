
package com.yazilimciakli.weather.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WeatherApi {

    @SerializedName("city")
    @Expose
    public City city;

    @SerializedName("cod")
    @Expose
    public String cod;

    @SerializedName("message")
    @Expose
    public String message;

    @SerializedName("cnt")
    @Expose
    public Integer cnt;

    @SerializedName("list")
    @Expose
    public java.util.List<List> list = null;

}
