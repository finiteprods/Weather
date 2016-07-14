package com.develogical;

import com.weather.Day;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class TestCachingTemperatureService {

    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();

    TemperatureService downstream = context.mock(TemperatureService.class);

    @Test
    public void preventMultipleRequestsDownstreamForSameParams() {
        TemperatureService tempSvc = new CachingTemperatureService(downstream);

        // gonna call tempSvc.temperatureFor() twice - but expect that the internal (i.e. downstream)
        // temperature svc will only get invoked once (with result cached)
        // mock its return value - assume it returns 11, say

        context.checking(new Expectations(){
            {
                exactly(1).of(downstream).temperatureFor("London", Day.MONDAY);
                will(returnValue(11));
            }
        });

        assertThat(tempSvc.temperatureFor("London", Day.MONDAY), is(11));
        assertThat(tempSvc.temperatureFor("London", Day.MONDAY), is(11));
    }

    @Test
    public void purgeOldestWhenCacheFull() {

        // create a service w/ cache capacity 1
        TemperatureService tempSvc = new CachingTemperatureService(downstream, 1);

        // gonna query monday (gets cached), then tuesday (replaces it)
        // then monday (not in cache anymore, so called again)

        context.checking(new Expectations(){
            {
                exactly(2).of(downstream).temperatureFor("London", Day.MONDAY);
                exactly(1).of(downstream).temperatureFor("London", Day.TUESDAY);
            }
        });

        tempSvc.temperatureFor("London", Day.MONDAY);
        tempSvc.temperatureFor("London", Day.TUESDAY);
        tempSvc.temperatureFor("London", Day.MONDAY);
    }
}
