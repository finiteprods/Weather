package com.develogical;

import com.weather.Day;

import java.util.LinkedHashMap;
import java.util.Map;


// i think this is a use of the decorator pattern
// take an existing TS and wrap caching behaviour on top
public class CachingTemperatureService implements TemperatureService {

    private TemperatureService temperatureService;
    private int capacity;
    private Map<Pair<String, Day>, Integer> cache;

    public CachingTemperatureService(TemperatureService temperatureService) {
        this(temperatureService, 1000);
    }

    public CachingTemperatureService(TemperatureService downstream, final int capacity) {
        this.temperatureService = downstream;
        this.capacity = capacity;

        // when does cache remove its oldest entry?
        // when it's exceeded capacity
        cache = new LinkedHashMap<Pair<String, Day>, Integer>() {
            @Override
            protected boolean removeEldestEntry(Map.Entry eldest) {
                return size() > capacity;
            }
        };
    }

    @Override
    public int temperatureFor(String place, Day day) {

        Pair<String, Day> key = new Pair<>(place, day);

        if (!cache.containsKey(key))
            cache.put(key, temperatureService.temperatureFor(place, day));

        return cache.get(key);
    }

}
