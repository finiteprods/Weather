package com.develogical;

import com.weather.Day;

/**
 * Created by kwok on 13/07/2016.
 */
public interface TemperatureService {
    int temperatureFor(String place, Day day);
}
