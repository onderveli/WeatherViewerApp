
package com.yazilimciakli.weather.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class List {

    @SerializedName("dt")
    @Expose
    public Long dt;

    @SerializedName("temp")
    @Expose
    public Temp temp;

    @SerializedName("pressure")
    @Expose
    public Double pressure;

    @SerializedName("humidity")
    @Expose
    public Double humidity;

    @SerializedName("weather")
    @Expose
    public java.util.List<Weather> weather = null;

    @SerializedName("speed")
    @Expose
    public Double speed;

    @SerializedName("deg")
    @Expose
    public Double deg;

    @SerializedName("clouds")
    @Expose
    public Double clouds;

    @SerializedName("snow")
    @Expose
    public Double snow;

}
