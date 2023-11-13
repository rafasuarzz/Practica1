package dacd.suarez.control;

import dacd.suarez.model.Location;
import dacd.suarez.model.Weather;

import java.time.Instant;

public interface WeatherProvider {
    Weather getWeather(Location location, Instant dt);
}
