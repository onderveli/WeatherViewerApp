package com.yazilimciakli.weather.Models;

public class Rain
{
    private String h;

    public String get3h ()
    {
        return h;
    }

    public void set3h (String h)
    {
        this.h = h;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [3h = "+h+"]";
    }
}