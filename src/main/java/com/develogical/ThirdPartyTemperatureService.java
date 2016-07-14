package com.develogical;

import com.weather.Day;
import com.weather.Forecaster;
import com.weather.Region;

// an adapter class
// Example.java shows how the 3rd party library is used - for our purposes we only really
// care about how to use it to get the temperature, hence this adapter
public class ThirdPartyTemperatureService implements TemperatureService {

    @Override
    public int temperatureFor(String place, Day day) {
        return new Forecaster().forecastFor(Region.valueOf(place.toUpperCase()), day)
                               .temperature();
    }
}
