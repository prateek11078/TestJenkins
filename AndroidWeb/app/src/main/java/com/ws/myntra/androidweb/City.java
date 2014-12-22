package com.ws.myntra.androidweb;

/**
 * Created by prateek.mehra on 20/12/14.
 */
public class City {
    public String name;
    public String countryCode;
    public String wiki;
    public long population;

    @Override
    public String toString() {
        return "Name:"+name+"\nCountry Code:"+countryCode+"\nPopulation:"+ population;
    }
}
