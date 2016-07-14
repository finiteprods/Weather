package com.develogical;

import com.weather.Day;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

public class TestThirdPartyTemperatureService {

    // this is more an integration test than unit test
    @Test
    public void canRetrieveTemperatureData() {

        TemperatureService thirdParty = new ThirdPartyTemperatureService();

        int temperature = thirdParty.temperatureFor("London", Day.FRIDAY);

        // obviously we can't predict the weather but > -20 degrees seems sensible
        assertThat(temperature, is(greaterThan(-20)));
    }
}
